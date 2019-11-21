# WanAndroid_java
### 根据鸿洋大神提供的wanandroid api，编写一个属于自己wanandorid项目（java版）,更多版本请看[https://github.com/zziafyc][1]

> 项目通过使用注解、工厂模式、代理模式、策略模式等，整体上解决代码冗余、内存泄露、Presenter生命周期以及数据存储问题，也是对mvp模式的高级抽象封装，喜欢mvp架构的小伙伴可以参考下（整个架构参考了 [刘镓旗的perfect-mvp][2]）。


1、将presenter通过注解的方式引入
```

/**
 * @作者 zziafyc
 * @创建日期 2019/7/28
 * @description 标注创建Presenter的注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<? extends BasePresenter> value();
}
```
2.通过工厂模式创建IPresenterFactory接口
```
/**
 * @作者 zziafyc
 * @创建日期 2019/7/28
 * @description 创建present的工厂接口
 */
public interface IPresenterFactory<V extends BaseView, P extends BasePresenter<V>> {

    /**
     * 创建Presenter
     *
     * @return presenter
     */
    P createPresenter();
}
```
3.创建present的工厂接口的实现类PresenterFactoryImpl
```
public class PresenterFactoryImpl<V extends BaseView, P extends BasePresenter<V>> implements IPresenterFactory<V, P> {
    /**
     * 需要创建的Presenter的类型
     */
    private final Class<P> mPresenterClass;

    public PresenterFactoryImpl(Class<P> presenterClass) {
        this.mPresenterClass = presenterClass;
    }

    @Override
    public P createPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter create failed!，" +
                    "please check if declaration @CreatePresenter(xxx.class) anotation or not");
        }
    }

    /**
     * 根据注解创建Presenter的工厂实现类
     *
     * @param clazz 需要创建Presenter的V层实现类
     * @param <V>   当前View实现的接口类型
     * @param <P>   当前要创建的Presenter类型
     * @return 工厂类
     */
    public static <V extends BaseView, P extends BasePresenter<V>> PresenterFactoryImpl<V, P> createFactory(Class<?> clazz) {
        CreatePresenter annotation = clazz.getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if (annotation != null) {
            aClass = (Class<P>) annotation.value();
        }
        return aClass == null ? null : new PresenterFactoryImpl<>(aClass);
    }
}
```
4.通过代理模式，创建presenter的代理接口IPresenterProxy
```
public interface IPresenterProxy<V extends BaseView, P extends BasePresenter<V>> {
    /**
     * 设置创建presenter的工厂
     *
     * @param presenterFactory 工厂类型
     */

    void setPresenterFactory(IPresenterFactory<V, P> presenterFactory);

    /**
     * 获取presenter的工厂类
     *
     * @return IPresenterFactory
     */

    IPresenterFactory<V, P> getPresenterFactory();

    /**
     * 获取创建的presenter
     *
     * @return 指定类型的presenter
     */

    P getPresenter();

}
```
5.创建代理接口IPresenterProxy的实现类PresenterProxyImpl
```
public class PresenterProxyImpl<V extends BaseView, P extends BasePresenter<V>> implements IPresenterProxy<V, P> {
    /**
     * 获取onSaveInstanceState中bundle的key
     */
    private static final String PRESENTER_KEY = "presenter_key";
    private P mPresenter;
    private Bundle mBundle;
    private boolean mIsBindView;

    private IPresenterFactory<V, P> mFactory;

    public PresenterProxyImpl(IPresenterFactory<V, P> factory) {
        this.mFactory = factory;
    }

    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("这个方法只能在getPresenter()之前调用，如果Presenter已经创建则不能再修改");
        }
        this.mFactory = presenterFactory;
    }

    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mFactory;
    }

    /**
     * 获取创建的Presenter
     *
     * @return 指定类型的Presenter
     * 如果之前创建过，而且是以外销毁则从Bundle中恢复
     */
    @Override
    public P getPresenter() {
        if (mFactory != null) {
            if (mPresenter == null) {
                mPresenter = mFactory.createPresenter();
                mPresenter.onCreatePresenterBundle(mBundle);
            }
        }
        return mPresenter;
    }

    /**
     * 绑定presenter和view
     *
     * @param view
     */
    public void onCreate(V view) {
        getPresenter();
        if (mPresenter != null && !mIsBindView && view != null) {
            mPresenter.onBindView(view);
            mIsBindView = true;
        }
    }

    /**
     * 销毁presenter创建的view
     */
    public void onUnbindView() {
        if (mPresenter != null && mIsBindView) {
            mPresenter.onUnBindView();

        }
    }

    /**
     * 销毁presenter
     */
    public void onDestroy() {
        if (mPresenter != null) {
            onUnbindView();
            mPresenter.OnDestroyPresenter();
            mPresenter = null;

        }
    }

    /**
     * 意外销毁的时候调用，存入回调给Presenter的Bundle和当前Presenter的id
     *
     * @return
     */
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getPresenter();
        if (mPresenter != null) {
            Bundle presenterBundle = new Bundle();
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY, presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复Presenter
     *
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }
}

```
6.封装BaseAcitivity
```
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity
        implements IPresenterProxy<V, P>, BaseViewInterface {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    private String TAG = this.getClass().getName();
    private PresenterProxyImpl<V, P> mPresenterProxy = new PresenterProxyImpl<>
            (PresenterFactoryImpl.<V, P>createFactory(getClass()));
    private Unbinder mUnbinder;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mPresenterProxy.onSaveInstanceState());
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenterProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mPresenterProxy.onCreate((V) this);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData(savedInstanceState);
    }


    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterFactory) {
        mPresenterProxy.setPresenterFactory(presenterFactory);

    }

    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mPresenterProxy.getPresenterFactory();
    }

    @Override
    public P getPresenter() {
        return mPresenterProxy.getPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterProxy.onDestroy();
        mUnbinder.unbind();
    }

}
```
7.封装BaseView和BaseViewInterface
```
public interface BaseView {

}

public interface BaseViewInterface {
    /**
     * get布局id
     *
     * @return
     */
    int getLayoutId();

    /**
     * 初始化布局
     *
     * @return
     */
    void initView();

    /**
     * 初始化数据
     */

    void initData(@Nullable Bundle savedInstanceState);
}
```
8.封装BasePresenter
```
public abstract class BasePresenter<V extends BaseView> {
    private V baseView;
    private String TAG = this.getClass().getName();
    public ApiUtils mApiUtils;

    /**
     * presenter初始化
     */
    public BasePresenter() {
        mApiUtils = ApiFactory.getApiUtil();
    }

    /**
     * Presenter创建的时候使用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePresenterBundle(@Nullable Bundle savedState) {
    }

    /**
     * 绑定view
     */
    public void onBindView(V view) {
        this.baseView = view;

    }

    /**
     * 解绑view
     */
    public void onUnBindView() {
        this.baseView = null;
    }

    /**
     * presenter销毁时的操作
     */
    public void OnDestroyPresenter() {

    }

    /**
     * presenter 意外销毁时，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        LogUtil.d(TAG, " presenter onSaveInstanceState");
    }

    /**
     * 获取当前view
     *
     * @return 获取view
     */

    public V getView() {
        return this.baseView;
    }
}

```


  最后，奉上这个项目的github地址：[https://github.com/zziafyc/WanAndroid_java][3]，欢迎大家star，谢谢~ ^-^


  [1]: https://github.com/zziafyc
  [2]: https://github.com/ljqloveyou123/perfect-mvp
  [3]: https://github.com/zziafyc/WanAndroid_java
### 抽离出Base类。
* BaseView ——— 抽象出来的需要Context环境的调用接口 
* Presenter ——— 为实现架构搭建，由BasePresenter实现接口
* ViewWrapper ——— 为实现架构搭建，由BaseViewWrapper实现接口
* BaseActivity ——— 抽象出来的Base，即可由MvpVmActivity继承，也可由业务Activity继承
* BasePresenter ——— 抽象出来的MvpVm架构的Presenter基类
* BaseViewWrapper ——— 抽象出来的MvpVm架构的ViewWrapper基类
* MvpVmActivity ——— 抽象出来的MvpVm架构的Activity基类
* MvpVmFragment ——— 抽象出来的MvpVm架构的Fragment基类
* DemoActivity


#### BaseView
```java
public interface BaseView {

public void setTitle(int titleId);

public void setTitle(String title);

public void showToast(int resId);

public void showToast(String msg);

public void showWaitDialog(int resId);

public void showWaitDialog(String message);

....
}
```

#### Presenter
```java
public interface Presenter<V, VW> {
    void attachView(V view);
    
    void setViewWrapper(VW viewWrapper);
    
    void detachView();
}
```

#### ViewWrapper
```java
public interface ViewWrapper<V, D> {
    void attachView(V view);
    
    void detachView();
    
    void setBinding(D dataBinding);
    
    void onBind();
}
```
#### BaseActivity
```java
public class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity implements BaseView {

    ...
    
    protected D dataBinding;
    protected BaseActivity activity;
    private BaseActivityDataBinding baseBinding;

    protected <D extends ViewDataBinding> D generateDataBinding(@LayoutRes int layoutResID) {
        D binding;
        if (hasToolBar()) {
            baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
            
            ...
            
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            binding = DataBindingUtil.inflate(inflater, layoutResID, baseBinding.contentLayout, true);
        } else {
            binding = DataBindingUtil.setContentView(this, layoutResID);
        }
        activity = this;
        
        ...
        
        return binding;
    }
}
```
generateDataBinding替换一般情况下调用的setContentView(int layoutId)，在业务Avtivity中第一步调用dataBinding = generateDataBinding(R.layout.xxx)即返回对应layout的DataBinding，详情请看DemoActivity。

#### BasePresenter
```java
public class BasePresenter<V, VW> implements Presenter<V, VW> {

    public V view;
    protected VW viewWrapper;
    ...
    
    @Override
    public void attachView(V view) {
        this.view = view;
    }
    
    @Override
    public void setViewWrapper(VW viewWrapper) {
        this.viewWrapper = viewWrapper;
    }
    
    @Override
    public void detachView() {
        view = null;
        viewWrapper = null;
        ...
    }
    
    ...
	
}
```

#### BaseViewWrapper
```java
public class BaseViewWrapper<V, D extends ViewDataBinding> implements ViewWrapper<V, D> {
    protected V view;
    protected D dataBinding;
        
    @Override
    public void attachView(V view) {
        this.view = view;
    }
        
    @Override
    public void detachView() {
        view = null;
        if (dataBinding != null) {
            dataBinding.unbind();
            dataBinding = null;
        }
    }
        
    @Override
    public void setBinding(D dataBinding) {
        this.dataBinding = dataBinding;
        onBind();
    }

    @Override
    public void onBind() {
        
    }
    
}
```
onBind方法由业务ViewWrapper实现，具体操作为DataBinding的数据绑定，以及listener等事件的设置。

#### MvpVmActivity
```java
public abstract class MvpVmActivity<P extends BasePresenter, VW extends BaseViewWrapper, D extends ViewDataBinding>
extends BaseActivity<D> {

    protected P presenter;
    protected VW viewWrapper;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        viewWrapper = createViewWrapper();
        if (presenter != null && viewWrapper != null) {
            presenter.setViewWrapper(viewWrapper);
        }
    }
    
    protected abstract P createPresenter();
    
    protected abstract VW createViewWrapper();
    
    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        if (viewWrapper != null) {
            viewWrapper.detachView();
        }
        if (dataBinding != null) {
            dataBinding.unbind();
            dataBinding = null;
        }
        super.onDestroy();
    }
}
```
createPresenter和createViewWrapper由业务Activity继承实现，实例化对应业务的Presenter和ViewWrapper。

#### MvpVmFragment
```java
public abstract class MvpVmFragment<P extends BasePresenter, VW extends BaseViewWrapper, D extends ViewDataBinding>
    extends BaseFragment<D> {
    
    protected P presenter;
    protected VW viewWrapper;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        viewWrapper = createWrapper();
        if (presenter != null && viewWrapper != null) {
            presenter.setViewWrapper(viewWrapper);
        }
    }
    
    protected abstract P createPresenter();
    
    protected abstract VW createWrapper();
    
    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detachView();
        }
        if (viewWrapper != null) {
            viewWrapper.detachView();
        }
        if (dataBinding != null) {
            dataBinding.unbind();
            dataBinding = null;
        }
        super.onDestroyView();
    }
}
```
createPresenter和createViewWrapper由业务Activity继承实现，实例化对应业务的Presenter和ViewWrapper，但是DataBinding实例的获取与在Activity中略有不同，是在onCreateView中获取的。

#### DemoActivity
```java
public class MvpVmDemoActivity extends MvpVmActivity<MvpVmDemoPresenter, MvpVmDemoViewWrapper, MvpVmDemoDataBinding>
    implements MvpVmDemoContract.View {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = generateDataBinding(R.layout.activity_mvpvm_demo);
        if (viewWrapper != null) {
            viewWrapper.setBinding(dataBinding);
        }
        presenter.fetchData();
    }
    
    @Override
    protected MvpVmDemoPresenter createPresenter() {
        return new MvpVmDemoPresenter(this);
    }
    
    @Override
    protected MvpVmDemoViewWrapper createViewWrapper() {
        return new MvpVmDemoViewWrapper(this);
    }
    
    ...
    
}
```
在onCreate第一步就是生成对应的DataBinding实例，然后设置给ViewWrapper，实现数据绑定关系，在createPresenter和createViewWrapper中实例化对应业务的presenter和viewWrapper。

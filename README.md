# MVP-ViewModel
这是一个采用MVP模式+ViewModel的框架，目的是为了方便安卓前端框架搭建。

View层主要包括Activity和Fragment，起事件处理分发的过度及dialog和跳转的处理作用。

在View层添加了一个wrapper的封装层，负责和viewmodel进行数据渲染交互。

Presenter负责数据的获取，通过Wrapper进行数据渲染。

后续会添加更多组件及Rxjava+Retrofit的网络处理框架。

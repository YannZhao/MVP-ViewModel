package com.baha.base.view.wrapper;

/**
 * Created by Yann on 07/01/2017.
 */

public interface ViewWrapper<V, D> {
	void attachView(V view);

	void detachView();

	void setBinding(D dataBinding);

	void onBind();
}

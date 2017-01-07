package com.baha.base.presenter;

/**
 * Created by Yann on 07/01/2017.
 */

public interface Presenter<V, VW> {
	void attachView(V view);

	void setViewWrapper(VW viewWrapper);

	void detachView();
}

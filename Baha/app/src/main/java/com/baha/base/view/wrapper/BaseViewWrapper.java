package com.baha.base.view.wrapper;

import android.databinding.ViewDataBinding;

/**
 * Created by Yann on 07/01/2017.
 */

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

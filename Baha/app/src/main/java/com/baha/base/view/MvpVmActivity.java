package com.baha.base.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baha.base.presenter.BasePresenter;
import com.baha.base.view.wrapper.BaseViewWrapper;

/**
 * Created by Yann on 07/01/2017.
 */

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

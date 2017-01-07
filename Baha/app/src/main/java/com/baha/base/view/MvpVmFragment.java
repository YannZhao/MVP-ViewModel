package com.baha.base.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.baha.base.presenter.BasePresenter;
import com.baha.base.view.wrapper.BaseViewWrapper;

/**
 * Created by Yann on 07/01/2017.
 */

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

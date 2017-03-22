package com.baha.base.view;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import android.app.Fragment;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.baha.rx.RxSchedulers;
import com.baha.utils.BahaUtil;

/**
 * Created by Yann on 07/01/2017.
 */

public abstract class BaseFragment<D extends ViewDataBinding> extends Fragment implements BaseView {

	public BaseActivity activity;
	private boolean isDestroyed = false;
	protected D dataBinding;
	private CompositeSubscription mCompositeSubscription;

	protected <D extends ViewDataBinding> D generateDataBinding(LayoutInflater inflater, ViewGroup container,
			@LayoutRes int layoutResID) {
		D dataBinding;
		dataBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false);
		return dataBinding;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		activity = (BaseActivity) getActivity();
		if (getView() != null) {
			setVisibleHint(isVisibleToUser);
		}
	}

	protected void setVisibleHint(boolean isVisibleToUser) {

	}

	@Override
	public void onDestroyView() {
		onUnSubscribe();
		activity = null;
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		isDestroyed = true;
		super.onDestroy();
	}

	public void onUnSubscribe() {
		if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
			mCompositeSubscription.unsubscribe();
		}
	}

	public Subscription addSubscription(Observable observable, Subscriber subscriber) {
		if (mCompositeSubscription == null) {
			mCompositeSubscription = new CompositeSubscription();
		}
		Subscription subscription = observable.compose(RxSchedulers.<String> applySchedulers()).subscribe(subscriber);
		mCompositeSubscription.add(subscription);
		return subscription;
	}

	public Subscription addSubscription(Subscription subscription) {
		if (mCompositeSubscription == null) {
			mCompositeSubscription = new CompositeSubscription();
		}
		mCompositeSubscription.add(subscription);
		return subscription;
	}

	@Override
	public void setTitle(int titleId) {
		if (activity != null) {
			activity.setTitle(titleId);
		}
	}

	@Override
	public void setTitle(String title) {
		if (activity != null) {
			activity.setTitle(title);
		}
	}

	@Override
	public void showToast(int resId) {
		BahaUtil.showToast(resId);
	}

	@Override
	public void showToast(String msg) {
		BahaUtil.showToast(msg);
	}

	@Override
	public void showWaitDialog(int resId) {
		if (activity != null) {
			activity.showWaitDialog(resId);
		}
	}

	@Override
	public void showWaitDialog(String message) {
		if (activity != null) {
			activity.showWaitDialog(message);
		}
	}

	@Override
	public void showWaitDialog(int stringId, boolean isCancelable, DialogInterface.OnCancelListener listener) {
		if (activity != null) {
			activity.showWaitDialog(stringId, isCancelable, listener);
		}
	}

	@Override
	public void showWaitDialog(String message, boolean isCancelable, DialogInterface.OnCancelListener listener) {
		if (activity != null) {
			activity.showWaitDialog(message, isCancelable, listener);
		}
	}

	@Override
	public void showWaitDialog(String message, Subscription cancelableTask) {
		if (activity != null) {
			activity.showWaitDialog(message, cancelableTask);
		}
	}

	@Override
	public void showWaitDialog(int stringId, Subscription cancelableTask) {
		if (activity != null) {
			activity.showWaitDialog(stringId, cancelableTask);
		}
	}

	@Override
	public void showWaitDialog(String message, Subscription cancelableTask, Runnable postCancelRunnable) {
		if (activity != null) {
			activity.showWaitDialog(message, cancelableTask, postCancelRunnable);
		}
	}

	@Override
	public void showWaitDialog(int stringId, Subscription cancelableTask, Runnable postCancelRunnable) {
		if (activity != null) {
			activity.showWaitDialog(stringId, cancelableTask, postCancelRunnable);
		}
	}

	@Override
	public void dismissWaitDialog() {
		if (activity != null) {
			activity.dismissWaitDialog();
		}
	}

	@Override
	public boolean isDestroyed() {
		return false;
	}
}

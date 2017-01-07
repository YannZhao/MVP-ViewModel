package com.baha.base.view;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.baha.R;
import com.baha.base.viewmodel.ToolbarModel;
import com.baha.databinding.BaseActivityDataBinding;
import com.baha.rx.RxSchedulers;
import com.baha.statusbar.StatusBarUtil;
import com.baha.utils.BahaUtil;

/**
 * Created by Yann on 07/01/2017.
 */

public class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity implements BaseView {

	private boolean destroyed = false;
	private Dialog waitDialog;
	private Subscription currentFrontTask = null;
	private CompositeSubscription mCompositeSubscription;
	protected D dataBinding;
	protected BaseActivity activity;
	private BaseActivityDataBinding baseBinding;

	protected <D extends ViewDataBinding> D generateDataBinding(@LayoutRes int layoutResID) {
		D binding;
		if (hasToolBar()) {
			baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
			bindToolbar();
			if (hasTabLayout()) {
				baseBinding.getToolbarModel().setShowTabLayout(true);
			} else {
				baseBinding.getToolbarModel().setShowTabLayout(false);
			}
			baseBinding.toolbarLayout.toolbar.setNavigationIcon(R.drawable.navi_bar_back_img_white);
			baseBinding.toolbarLayout.toolbar.setTitleTextColor(Color.WHITE);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			binding = DataBindingUtil.inflate(inflater, layoutResID, baseBinding.contentLayout, true);
		} else {
			binding = DataBindingUtil.setContentView(this, layoutResID);
		}
		activity = this;
		initToolBar(!isCenterTitle());
		setStatusBar();
		return binding;
	}

	private void bindToolbar() {
		ToolbarModel toolbarModel = new ToolbarModel();
		baseBinding.setToolbarModel(toolbarModel);
	}

	protected void setStatusBar() {
		StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark), 0);
	}

	public Toolbar initToolBar(boolean leftTitleEnabled) {
		if (!hasToolBar()) {
			return null;
		}
		setSupportActionBar(baseBinding.toolbarLayout.toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(leftTitleEnabled);
			actionBar.setDisplayShowTitleEnabled(leftTitleEnabled);
		}
		if (!leftTitleEnabled) {
			setTitle(getTitle() != null ? getTitle().toString() : null);
		}
		return baseBinding.toolbarLayout.toolbar;
	}

	@Override
	protected void onDestroy() {
		destroyed = true;
		activity = null;
		onUnSubscribe();
		super.onDestroy();
	}

	private void cancelCurrentFrontTask() {
		if (currentFrontTask != null) {
			currentFrontTask.unsubscribe();
			currentFrontTask = null;
		}
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
	public void setTitle(String title) {
		if (hasToolBar() && isCenterTitle()) {
			baseBinding.toolbarLayout.tvCenterTitle.setText(title);
		} else {
			super.setTitle(title);
		}
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void showToast(int resId) {
		BahaUtil.showToast(resId);
	}

	@Override
	public void showToast(String msg) {
		BahaUtil.showToast(msg);
	}

	private boolean createWaitDialog(String message) {
		return true;
	}

	@Override
	public void showWaitDialog(int resId) {
		showToast(getString(resId));
	}

	@Override
	public void showWaitDialog(String message) {
		if (createWaitDialog(message)) {
			waitDialog.setCancelable(false);
			waitDialog.show();
		}
	}

	@Override
	public void showWaitDialog(int stringId, boolean isCancelable, DialogInterface.OnCancelListener listener) {
		showWaitDialog(getString(stringId), isCancelable, listener);
	}

	@Override
	public void showWaitDialog(String message, boolean isCancelable, DialogInterface.OnCancelListener listener) {
		if (createWaitDialog(message)) {
			waitDialog.setCanceledOnTouchOutside(false);
			waitDialog.setCancelable(isCancelable);
			waitDialog.setOnCancelListener(listener);
			waitDialog.show();
		}
	}

	@Override
	public void showWaitDialog(String message, Subscription cancelableTask) {
		showWaitDialog(message, cancelableTask, null);
	}

	@Override
	public void showWaitDialog(int stringId, Subscription cancelableTask) {
		showWaitDialog(getString(stringId), cancelableTask);
	}

	@Override
	public void showWaitDialog(String message, Subscription cancelableTask, final Runnable postCancelRunnable) {
		cancelCurrentFrontTask();
		currentFrontTask = cancelableTask;
		addSubscription(cancelableTask);
		showWaitDialog(message, true, new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				cancelCurrentFrontTask();
				if (postCancelRunnable != null) {
					postCancelRunnable.run();
				}
			}
		});
	}

	@Override
	public void showWaitDialog(int stringId, Subscription cancelableTask, Runnable postCancelRunnable) {
		showWaitDialog(getString(stringId), cancelableTask, postCancelRunnable);
	}

	@Override
	public void dismissWaitDialog() {
		if (!isDestroyed() && waitDialog != null && waitDialog.isShowing()) {
			waitDialog.dismiss();
		}
		waitDialog = null;
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	public boolean hasToolBar() {
		return true;
	}

	public boolean hasTabLayout() {
		return false;
	}

	public boolean isCenterTitle() {
		return false;
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}
}

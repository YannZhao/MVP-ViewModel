package com.baha.base.view;

import rx.Subscription;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Yann on 07/01/2017.
 */

public interface BaseView {

	public void setTitle(int titleId);

	public void setTitle(String title);

	public void showToast(int resId);

	public void showToast(String msg);

	public void showWaitDialog(int resId);

	public void showWaitDialog(String message);

	public void showWaitDialog(int stringId, boolean isCancelable, DialogInterface.OnCancelListener listener);

	public void showWaitDialog(String message, boolean isCancelable, DialogInterface.OnCancelListener listener);

	public void showWaitDialog(String message, final Subscription cancelableTask);

	public void showWaitDialog(int stringId, final Subscription cancelableTask);

	public void showWaitDialog(String message, final Subscription cancelableTask, final Runnable postCancelRunnable);

	public void showWaitDialog(int stringId, final Subscription cancelableTask, final Runnable postCancelRunnable);

	public void dismissWaitDialog();

	public Context getContext();

	public Activity getActivity();

	public boolean isDestroyed();

}

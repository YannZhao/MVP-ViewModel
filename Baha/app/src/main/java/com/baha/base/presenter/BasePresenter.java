package com.baha.base.presenter;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import com.baha.rx.RxSchedulers;

/**
 * Created by Yann on 07/01/2017.
 */

public class BasePresenter<V, VW> implements Presenter<V, VW> {

	public V view;
	protected VW viewWrapper;
	private CompositeSubscription mCompositeSubscription;

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
		onUnSubscribe();
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
}

package com.baha.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxSchedulers {

	private final static Observable.Transformer schedulersTransformer = new Observable.Transformer() {
		@Override
		public Object call(Object observable) {
			return ((Observable) observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	};

	public static <T> Observable.Transformer<T, T> applySchedulers() {
		return (Observable.Transformer<T, T>) schedulersTransformer;
	}

}

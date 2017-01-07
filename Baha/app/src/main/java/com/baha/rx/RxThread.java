package com.baha.rx;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxThread {
	public static void execute(final Runnable runnable) {
		Observable.just(1).observeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
			@Override
			public void call(Integer integer) {
				if (runnable != null) {
					runnable.run();
				}
			}
		});
	}
}

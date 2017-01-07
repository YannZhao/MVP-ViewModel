package com.baha.rx;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class RxCount {

	public static Observable<Integer> countdown(int time) {
		if (time < 0)
			time = 0;

		final int countTime = time;
		return Observable.interval(0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
				.map(new Func1<Long, Integer>() {
					@Override
					public Integer call(Long increaseTime) {
						return countTime - increaseTime.intValue();
					}
				}).take(countTime + 1);

	}

	public static Observable<Long> count() {
		return Observable.interval(0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
	}
}

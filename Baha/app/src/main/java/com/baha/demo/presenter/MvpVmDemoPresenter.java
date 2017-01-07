package com.baha.demo.presenter;

import java.util.ArrayList;

import com.baha.base.presenter.BasePresenter;
import com.baha.demo.PersonalInfo;
import com.baha.demo.contract.MvpVmDemoContract;

/**
 * Created by Yann on 07/01/2017.
 */

public class MvpVmDemoPresenter extends BasePresenter<MvpVmDemoContract.View, MvpVmDemoContract.ViewWrapper> implements
		MvpVmDemoContract.Presenter {

	public MvpVmDemoPresenter(MvpVmDemoContract.View view) {
		attachView(view);
	}

	@Override
	public void detachView() {
		super.detachView();
	}

	@Override
	public void fetchData() {
		ArrayList<PersonalInfo> infos = new ArrayList<>();
		infos.add(new PersonalInfo("Jack", "male", "basketball"));
		infos.add(new PersonalInfo("Yann", "male", "football"));
		infos.add(new PersonalInfo("Mary", "female", "badminton"));
		infos.add(new PersonalInfo("Robson", "male", "foosball"));
		infos.add(new PersonalInfo("Selina", "female", "tennis"));
		viewWrapper.setData(infos);
	}
}

package com.baha.demo.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.baha.BR;
import com.baha.demo.PersonalInfo;

/**
 * Created by Yann on 07/01/2017.
 */

public class MvpVmDemoModel extends BaseObservable {

	private PersonalInfo info;

	private String introduction;

	public PersonalInfo getInfo() {
		return info;
	}

	public void setInfo(PersonalInfo info) {
		this.info = info;
		String introduction = "I'm " + info.getName() + ", " + info.getGender() + ", I like " + info.getHobbies();
		setIntroduction(introduction);
	}

	@Bindable
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
		notifyPropertyChanged(BR.introduction);
	}
}

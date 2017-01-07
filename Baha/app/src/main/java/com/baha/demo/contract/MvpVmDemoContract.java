package com.baha.demo.contract;

import java.util.List;

import com.baha.base.view.BaseView;
import com.baha.demo.PersonalInfo;

/**
 * Created by Yann on 07/01/2017.
 */

public class MvpVmDemoContract {

	public interface View extends BaseView {
		void showPersonalInfoDialog(PersonalInfo info);
	}

	public interface Presenter {
		void fetchData();
	}

	public interface ViewWrapper {
		void setData(List<PersonalInfo> infos);
	}
}

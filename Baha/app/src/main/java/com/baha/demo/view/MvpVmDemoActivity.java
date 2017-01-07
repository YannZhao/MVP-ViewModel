package com.baha.demo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.baha.R;
import com.baha.base.view.MvpVmActivity;
import com.baha.common.dialog.CustomDialog;
import com.baha.common.dialog.listener.OnOkClickListener;
import com.baha.databinding.MvpVmDemoDataBinding;
import com.baha.demo.PersonalInfo;
import com.baha.demo.contract.MvpVmDemoContract;
import com.baha.demo.presenter.MvpVmDemoPresenter;
import com.baha.demo.view.wrapper.MvpVmDemoViewWrapper;

/**
 * Created by Yann on 07/01/2017.
 */

public class MvpVmDemoActivity extends MvpVmActivity<MvpVmDemoPresenter, MvpVmDemoViewWrapper, MvpVmDemoDataBinding>
		implements MvpVmDemoContract.View {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = generateDataBinding(R.layout.activity_mvpvm_demo);
		if (viewWrapper != null) {
			viewWrapper.setBinding(dataBinding);
		}
        presenter.fetchData();
	}

	@Override
	protected MvpVmDemoPresenter createPresenter() {
		return new MvpVmDemoPresenter(this);
	}

	@Override
	protected MvpVmDemoViewWrapper createViewWrapper() {
		return new MvpVmDemoViewWrapper(this);
	}

	@Override
	public void showPersonalInfoDialog(PersonalInfo info) {
		String content = "name: " + info.getName() + "\n" + "gender: " + info.getGender() + "\n" + "hobby: "
				+ info.getHobbies();
		final CustomDialog dialog = new CustomDialog(this, CustomDialog.CONVERSATION_TYPE);
		dialog.show();
		dialog.setCustomTitle("Personal Information").setContent(content).setSingle(true)
				.setOkBtnClickListener("confirm", new OnOkClickListener() {
					@Override
					public void onOkClicked(View clickedView) {
						dialog.dismiss();
					}
				});
	}
}

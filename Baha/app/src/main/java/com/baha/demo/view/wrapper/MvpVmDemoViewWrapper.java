package com.baha.demo.view.wrapper;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baha.base.view.wrapper.BaseViewWrapper;
import com.baha.common.listener.DataBindingRecyclerViewItemClickListener;
import com.baha.databinding.MvpVmDemoDataBinding;
import com.baha.demo.PersonalInfo;
import com.baha.demo.contract.MvpVmDemoContract;
import com.baha.demo.view.adapter.MvpVmDemoAdapter;

/**
 * Created by Yann on 07/01/2017.
 */

public class MvpVmDemoViewWrapper extends BaseViewWrapper<MvpVmDemoContract.View, MvpVmDemoDataBinding> implements
		MvpVmDemoContract.ViewWrapper, DataBindingRecyclerViewItemClickListener<PersonalInfo> {

	private MvpVmDemoAdapter adapter;

	public MvpVmDemoViewWrapper(MvpVmDemoContract.View view) {
		attachView(view);
	}

	@Override
	public void onBind() {
		adapter = new MvpVmDemoAdapter(view.getContext());
		adapter.setOnPersonalInfoItemClickListener(this);
		dataBinding.demoListRecyclerView.setHasFixedSize(true);
		dataBinding.demoListRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
		dataBinding.demoListRecyclerView.setAdapter(adapter);
	}

	@Override
	public void setData(List<PersonalInfo> infos) {
		adapter.addAll(infos);
	}

	@Override
	public void onItemClick(View clickedView, PersonalInfo info) {
		view.showPersonalInfoDialog(info);
	}
}

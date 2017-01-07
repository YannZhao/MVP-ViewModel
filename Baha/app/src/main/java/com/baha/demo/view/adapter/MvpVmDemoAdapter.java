package com.baha.demo.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.baha.R;
import com.baha.common.adapter.RecyclerViewDataBindingAdapter;
import com.baha.common.adapter.DataBindingViewHolder;
import com.baha.common.listener.DataBindingRecyclerViewItemClickListener;
import com.baha.databinding.MvpVmDemoItemDataBinding;
import com.baha.demo.PersonalInfo;
import com.baha.demo.viewmodel.MvpVmDemoModel;

/**
 * Created by Yann on 07/01/2017.
 */

public class MvpVmDemoAdapter extends RecyclerViewDataBindingAdapter<PersonalInfo> {

	private LayoutInflater layoutInflater;

	private DataBindingRecyclerViewItemClickListener<PersonalInfo> listener;

	public MvpVmDemoAdapter(Context context) {
		super(context);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public ViewHolder createDataBindingViewHolder(ViewGroup parent, int viewType) {
		MvpVmDemoItemDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_mvpvm_demo_item,
				parent, false);
		return new ViewHolder(dataBinding);
	}

	class ViewHolder extends DataBindingViewHolder<PersonalInfo, MvpVmDemoItemDataBinding> {

		public ViewHolder(MvpVmDemoItemDataBinding dataBinding) {
			super(dataBinding);
			if (listener != null) {
				dataBinding.setListener(listener);
			}
		}

		@Override
		public void setData(PersonalInfo data, MvpVmDemoItemDataBinding dataBinding) {
			if (data == null || dataBinding == null) {
				return;
			}
			MvpVmDemoModel model = new MvpVmDemoModel();
			model.setInfo(data);
			dataBinding.setDemoItemModel(model);
			dataBinding.executePendingBindings();
		}
	}

	public void setOnPersonalInfoItemClickListener(DataBindingRecyclerViewItemClickListener<PersonalInfo> listener) {
		this.listener = listener;
	}
}

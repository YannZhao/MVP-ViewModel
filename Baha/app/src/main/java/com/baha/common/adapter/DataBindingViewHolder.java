package com.baha.common.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Yann on 07/01/2017.
 */

public class DataBindingViewHolder<M, D extends ViewDataBinding> extends RecyclerView.ViewHolder {

	protected D dataBinding;

	public DataBindingViewHolder(View itemView) {
		super(itemView);
	}

	public DataBindingViewHolder(D dataBinding) {
		super(dataBinding.getRoot());
		this.dataBinding = dataBinding;
	}

	public void setData(M data) {
	}

	public void setData(M data, int position) {
	}

	public void setData(M data, D dataBinding) {
	}

	protected Context getContext() {
		return itemView.getContext();
	}

	public D getDataBinding() {
		return dataBinding;
	}
}

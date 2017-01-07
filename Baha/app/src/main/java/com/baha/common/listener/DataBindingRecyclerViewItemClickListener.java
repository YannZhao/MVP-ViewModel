package com.baha.common.listener;

import android.view.View;

/**
 * Created by Yann on 07/01/2017.
 */

public interface DataBindingRecyclerViewItemClickListener<M> {
	void onItemClick(View view, M t);
}

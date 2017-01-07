package com.baha.common.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yann on 07/01/2017.
 */

public abstract class RecyclerViewDataBindingAdapter<M> extends RecyclerView.Adapter<DataBindingViewHolder> {

	private final Object mLock = new Object();

	private List<M> mObjects;

	private RecyclerView recyclerView;

	private Context mContext;

	private OnItemClickListener mItemClickListener;
	private OnItemLongClickListener mItemLongClickListener;

	public RecyclerViewDataBindingAdapter(Context context) {
		init(context, new ArrayList<M>());
	}

	public RecyclerViewDataBindingAdapter(Context context, M[] objects) {
		init(context, Arrays.asList(objects));
	}

	public RecyclerViewDataBindingAdapter(Context context, List<M> objects) {
		init(context, objects);
	}

	private void init(Context context, List<M> objects) {
		mContext = context;
		mObjects = objects;
	}

	@Override
	public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final DataBindingViewHolder viewHolder = createDataBindingViewHolder(parent, viewType);
		if (viewHolder.getDataBinding() != null) {
			viewHolder.getDataBinding().addOnRebindCallback(new OnRebindCallback() {
				@Override
				public boolean onPreBind(ViewDataBinding binding) {
					return recyclerView != null && recyclerView.isComputingLayout();
				}

				@Override
				public void onCanceled(ViewDataBinding binding) {
					if (recyclerView == null || recyclerView.isComputingLayout()) {
						return;
					}
					int position = viewHolder.getAdapterPosition();
					if (position != RecyclerView.NO_POSITION) {
						notifyItemChanged(position, DATA_INVALIDATION);
					}
				}
			});
		}
		if (mItemClickListener != null) {
			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mItemClickListener.onItemClick(viewHolder.getAdapterPosition(),
							getItem(viewHolder.getAdapterPosition()));
				}
			});
		}

		if (mItemLongClickListener != null) {
			viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					return mItemLongClickListener.onItemClick(viewHolder.getAdapterPosition(),
							getItem(viewHolder.getAdapterPosition()));
				}
			});
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(DataBindingViewHolder holder, int position) {
		if (isForDataBinding(getData())) {
			holder.getDataBinding().executePendingBindings();
		} else {
			holder.setData(getItem(position), holder.getDataBinding());
			holder.setData(getItem(position));
			holder.setData(getItem(position), position);
		}
	}

	public abstract DataBindingViewHolder createDataBindingViewHolder(ViewGroup parent, int viewType);

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		this.recyclerView = recyclerView;
	}

	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
		this.recyclerView = null;
	}

	public void addAll(Collection<? extends M> objects) {
		if (objects != null && objects.size() != 0) {
			synchronized (mLock) {
				mObjects.addAll(objects);
			}
		}
		notifyDataChanged();
	}

	@Override
	public int getItemCount() {
		return mObjects.size();
	}

	public List getData() {
		return mObjects;
	}

	public M getItem(int position) {
		try {
			return mObjects.get(position);
		} catch (Exception e) {
			return null;
		}
	}

	public Context getContext() {
		return mContext;
	}

	static Object DATA_INVALIDATION = new Object();

	private boolean isForDataBinding(List<Object> payloads) {
		if (payloads == null || payloads.size() == 0) {
			return false;
		}
		for (Object obj : payloads) {
			if (obj != DATA_INVALIDATION) {
				return false;
			}
		}
		return true;
	}

	private void notifyDataChanged() {
		if (canNotifyDataChanged()) {
			notifyDataSetChanged();
		}
	}

	private boolean canNotifyDataChanged() {
		if (recyclerView == null) {
			return false;
		}
		return recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.isComputingLayout();
	}

	public void setmItemClickListener(OnItemClickListener mItemClickListener) {
		this.mItemClickListener = mItemClickListener;
	}

	public void setmItemLongClickListener(OnItemLongClickListener mItemLongClickListener) {
		this.mItemLongClickListener = mItemLongClickListener;
	}

	public interface OnItemClickListener<M> {
		void onItemClick(int position, M data);
	}

	public interface OnItemLongClickListener<M> {
		boolean onItemClick(int position, M data);
	}
}

package com.baha.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baha.R;
import com.baha.common.dialog.listener.OnCancelClickListener;
import com.baha.common.dialog.listener.OnConfirmClickListener;
import com.baha.common.dialog.listener.OnOkClickListener;
import com.baha.common.dialog.viewmodel.CustomDialogModel;
import com.baha.databinding.DialogDataBinding;
import com.baha.utils.BahaUtil;

/**
 * Created by Yann on 07/01/2017.
 */

public class CustomDialog extends Dialog {

	public static final int PROGRESS_TYPE = 0;
	public static final int CONVERSATION_TYPE = 1;

	private int type;

	private DialogDataBinding dataBinding;

	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int type) {
		super(context, type == PROGRESS_TYPE ? R.style.dialogProgress : R.style.custom_dialog_style);
		this.type = type;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dialog);
		dataBinding = DataBindingUtil.bind(findViewById(R.id.dialog_layout));
		CustomDialogModel model = new CustomDialogModel();

		if (type == PROGRESS_TYPE) {
			model.setProgressStyle(true);
		} else {
			model.setProgressStyle(false);
		}
		dataBinding.setDialogModel(model);
	}

	public CustomDialog setCustomTitle(CharSequence title) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setTitle(title.toString());
		} else {
			super.setTitle(title);
		}
		return this;
	}

	public CustomDialog setCustomTitle(int titleId) {
		return this.setCustomTitle(getContext().getString(titleId));
	}

	public CustomDialog setSubtitle(String subtitle) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setSubTitle(subtitle);
		}
		return this;
	}

	public CustomDialog setSubtitle(int subtitleId) {
		return setSubtitle(getContext().getString(subtitleId));
	}

	public CustomDialog setContent(String content) {
		TextView tv = getDefaultContentView();
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv.setLineSpacing(1f, 1.2f);
		tv.setText(content);
		return setBody(tv);
	}

	public CustomDialog setContent(int contentId) {
		return this.setContent(getContext().getString(contentId));
	}

	public CustomDialog setBody(View view) {
		if (dataBinding != null) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			dataBinding.body.addView(view, lp);
		}
		return this;
	}

	public CustomDialog setExpand(View view) {
		if (dataBinding != null) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			dataBinding.expandContainer.addView(view, lp);
			dataBinding.getDialogModel().setHasExpand(true);
		}

		return this;
	}

	public CustomDialog setConfirmBtnClickListener(int textId, OnConfirmClickListener listener) {
		return this.setConfirmBtnClickListener(getContext().getString(textId), listener);
	}

	public CustomDialog setConfirmBtnClickListener(String text, OnConfirmClickListener listener) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setConfirmText(text);
			dataBinding.setOnConfirmClickListener(listener);
		}
		return this;
	}

	public CustomDialog setCancelBtnClickListener(int textId, OnCancelClickListener listener) {
		return this.setCancelBtnClickListener(getContext().getString(textId), listener);
	}

	public CustomDialog setCancelBtnClickListener(String text, OnCancelClickListener listener) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setCancelText(text);
			dataBinding.setOnCancelClickListener(listener);
		}
		return this;
	}

	public CustomDialog setOkBtnClickListener(int textId, OnOkClickListener listener) {
		return this.setOkBtnClickListener(getContext().getString(textId), listener);
	}

	public CustomDialog setOkBtnClickListener(String text, OnOkClickListener listener) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setOkText(text);
			dataBinding.setOnOkClickListener(listener);
		}
		return this;
	}

	private TextView getDefaultContentView() {
		TextView textView = new TextView(getContext());
		textView.setTextColor(ContextCompat.getColor(getContext(), R.color.common_dialog_content_color));
		textView.setTextSize(BahaUtil.px2dip(getContext().getResources().getDimensionPixelSize(
				R.dimen.dialog_content_text_size)));
		return textView;
	}

	public CustomDialog setSingle(boolean isSingle) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setSingleBtn(isSingle);
		}
		return this;
	}

	public CustomDialog setHeaderEnable(boolean hasHeader) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setHasHeader(hasHeader);
		}
		return this;
	}

	public CustomDialog setFooterEnable(boolean hasFooter) {
		if (dataBinding != null) {
			dataBinding.getDialogModel().setHasFooter(hasFooter);
		}
		return this;
	}
}

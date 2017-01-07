package com.baha.demo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baha.R;
import com.baha.base.view.BaseActivity;
import com.baha.common.dialog.CustomDialog;
import com.baha.common.dialog.listener.OnCancelClickListener;
import com.baha.common.dialog.listener.OnConfirmClickListener;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void showDialog(View view) {
		final CustomDialog dialog = new CustomDialog(this, CustomDialog.CONVERSATION_TYPE);
		dialog.show();
		dialog.setCustomTitle("test").setSubtitle("test").setContent("testtesttest").setSingle(false)
				.setConfirmBtnClickListener("confirm", new OnConfirmClickListener() {
					@Override
					public void onConfirmClicked(View clickedView) {
						dialog.dismiss();
					}
				}).setCancelBtnClickListener("cancel", new OnCancelClickListener() {
					@Override
					public void onCancelClicked(View clickedView) {
						dialog.dismiss();
					}
				});
	}

	public void goMvpVmDemo(View view) {
		startActivity(new Intent(this, MvpVmDemoActivity.class));
	}
}

package com.baha.common.dialog.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.baha.BR;

/**
 * Created by Yann on 07/01/2017.
 */

public class CustomDialogModel extends BaseObservable {

	private boolean progressStyle;

	private String progressText;

	private String title;

	private String subTitle;

	private String confirmText;

	private String cancelText;

	private String okText;

	private boolean singleBtn;

	private boolean hasExpand;

	private boolean hasHeader = true;

	private boolean hasFooter = true;

	@Bindable
	public boolean isProgressStyle() {
		return progressStyle;
	}

	public void setProgressStyle(boolean progressStyle) {
		this.progressStyle = progressStyle;
		notifyPropertyChanged(BR.progressStyle);
	}

	@Bindable
	public String getProgressText() {
		return progressText;
	}

	public void setProgressText(String progressText) {
		this.progressText = progressText;
		notifyPropertyChanged(BR.progressText);
	}

	@Bindable
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		notifyPropertyChanged(BR.title);
	}

	@Bindable
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		notifyPropertyChanged(BR.subTitle);
	}

	@Bindable
	public String getConfirmText() {
		return confirmText;
	}

	public void setConfirmText(String confirmText) {
		this.confirmText = confirmText;
		notifyPropertyChanged(BR.confirmText);
	}

	@Bindable
	public String getCancelText() {
		return cancelText;
	}

	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
		notifyPropertyChanged(BR.cancelText);
	}

	@Bindable
	public String getOkText() {
		return okText;
	}

	public void setOkText(String okText) {
		this.okText = okText;
		notifyPropertyChanged(BR.okText);
	}

	@Bindable
	public boolean isSingleBtn() {
		return singleBtn;
	}

	public void setSingleBtn(boolean singleBtn) {
		this.singleBtn = singleBtn;
		notifyPropertyChanged(BR.singleBtn);
	}

	@Bindable
	public boolean isHasExpand() {
		return hasExpand;
	}

	public void setHasExpand(boolean hasExpand) {
		this.hasExpand = hasExpand;
		notifyPropertyChanged(BR.hasExpand);
	}

	@Bindable
	public boolean isHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
		notifyPropertyChanged(BR.hasHeader);
	}

	@Bindable
	public boolean isHasFooter() {
		return hasFooter;
	}

	public void setHasFooter(boolean hasFooter) {
		this.hasFooter = hasFooter;
		notifyPropertyChanged(BR.hasFooter);
	}
}

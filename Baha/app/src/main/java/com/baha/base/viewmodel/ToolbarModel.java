package com.baha.base.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.baha.BR;

public class ToolbarModel extends BaseObservable {
	private String title;
	private String rightText;
	private boolean rightTextVisible;
	private boolean showTabLayout;
	private boolean showLeftTitle;

	@Bindable
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		notifyPropertyChanged(BR.title);
	}

	@Bindable
	public String getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = rightText;
		notifyPropertyChanged(BR.rightText);
	}

	@Bindable
	public boolean isRightTextVisible() {
		return rightTextVisible;
	}

	public void setRightTextVisible(boolean rightTextVisible) {
		this.rightTextVisible = rightTextVisible;
		notifyPropertyChanged(BR.rightTextVisible);
	}

	@Bindable
	public boolean isShowTabLayout() {
		return showTabLayout;
	}

	public void setShowTabLayout(boolean showTabLayout) {
		this.showTabLayout = showTabLayout;
		notifyPropertyChanged(BR.showTabLayout);
	}

	@Bindable
	public boolean isShowLeftTitle() {
		return showLeftTitle;
	}

	public void setShowLeftTitle(boolean showLeftTitle) {
		this.showLeftTitle = showLeftTitle;
		notifyPropertyChanged(BR.showLeftTitle);
	}
}

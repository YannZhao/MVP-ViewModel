package com.baha.base.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.baha.R;
import com.baha.databinding.BaseTabViewPagerDataBinding;

public abstract class BaseTabViewPagerFragmentActivity extends BaseActivity<BaseTabViewPagerDataBinding> {

	protected String[] tabTitles;
	protected Fragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataBinding = generateDataBinding(R.layout.activity_base_tablayout_view_pager_layout);
		init();
	}

	private void init() {
		dataBinding.viewpager.setAdapter(fragmentPagerAdapter);
		dataBinding.viewpager.setOffscreenPageLimit(tabTitles.length);
		getBaseDataBinding().toolbarLayout.tabLayout.setupWithViewPager(dataBinding.viewpager);
	}

	@Override
	public boolean hasTabLayout() {
		return true;
	}

	protected FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {

		@Override
		public Fragment getItem(int index) {
			return getFragment(index);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabTitles[position];
		}

		@Override
		public int getCount() {
			return tabTitles.length;
		}

	};

	abstract public Fragment getFragment(int index);

	protected void setCurrentItem(int index, boolean smoothScroll) {
		if (dataBinding.viewpager != null && index < tabTitles.length) {
			dataBinding.viewpager.setCurrentItem(index, smoothScroll);
		}
	}
}

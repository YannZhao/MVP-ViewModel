package com.baha.common.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.baha.utils.BahaUtil;

public class OverlapViewPager extends ViewGroup {

	public static final int PAGE_LEFT = -1;

	public static final int PAGE_MIDDLE = 0;

	public static final int PAGE_RIGHT = 1;

	private static final int PAGE_COUNT = 3;

	public static final int VIEW_INDEX_LEFT = 0;

	public static final int VIEW_INDEX_MIDDLE = 1;

	public static final int VIEW_INDEX_RIGHT = 2;

	private static final int MAX_VELOCITY = 8000;

	private static final int MIN_VELOCITY = 500;

	private static final int INVALID_POINTER = -1;

	private int downX = 0;

	private int mCurrentFront = PAGE_MIDDLE;

	private int mCurrentMovedItemIndex = Integer.MIN_VALUE;

	private int mLastMoveItemIndex = Integer.MIN_VALUE;

	private boolean isDragging = false;

	private Scroller mScroller = null;

	private int lastPointX = 0;

	private View dragLayout;

	private View[] views;

	private VelocityTracker mVelocityTracker;

	private int screenWidth;

	private int mActivePointerId = INVALID_POINTER;

	private boolean allowSlide = true;

	public OverlapViewPager(Context context) {
		super(context, null);
	}

	public OverlapViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(getContext(), new DecelerateInterpolator());
		screenWidth = BahaUtil.getDeviceWidth();
	}

	public void addViews(View[] views) {
		removeAllViews();
		this.views = views;
		int seq[] = { 1, 0, 2 };
		for (int i : seq) {
			if (views[i] != null) {
				addView(views[i], new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
		}
	}

	public void setViews(View[] views) {
		this.views = views;
		invalidate();
	}

	public void resetPages() {
		switch (mCurrentFront) {
			case PAGE_LEFT:
				mCurrentMovedItemIndex = VIEW_INDEX_LEFT;
				mCurrentFront = PAGE_MIDDLE;
				mScroller.startScroll(views[mCurrentMovedItemIndex].getLeft(), 0,
						0 - views[mCurrentMovedItemIndex].getWidth(), 0);
				invalidate();
				break;
			case PAGE_MIDDLE:
				break;
			case PAGE_RIGHT:
				mCurrentMovedItemIndex = VIEW_INDEX_RIGHT;
				mCurrentFront = PAGE_MIDDLE;
				mScroller.startScroll(views[mCurrentMovedItemIndex].getLeft(), 0,
						views[mCurrentMovedItemIndex].getWidth(), 0);
				invalidate();
				break;
		}
		checkToResetLastMovedView(mCurrentMovedItemIndex);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		super.onInterceptTouchEvent(ev);
		return gestureDetector.onTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!allowSlide) {
			return true;
		}
		int action = event.getAction();
		final int index = MotionEventCompat.getActionIndex(event);
		final int curPointId = MotionEventCompat.getPointerId(event, index);
		if (mActivePointerId != INVALID_POINTER && mActivePointerId != curPointId) {
			if (event.getPointerCount() == 1 && event.getAction() == MotionEvent.ACTION_UP) {
				mActivePointerId = INVALID_POINTER;
			}
			return true;
		}
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				if (isDragging) {
					if (mActivePointerId != INVALID_POINTER) {
						processActionMove(event);
					}
				} else {
					downX = (int) event.getX();
					lastPointX = (int) event.getX();
					isDragging = true;
					mActivePointerId = MotionEventCompat.getPointerId(event, 0);
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_UP: {
				if (isDragging && mActivePointerId != INVALID_POINTER) {
					processActionUp(event);
					isDragging = false;
				}
				break;
			}
		}
		return true;
	}

	private View getDragView(int index) {
		switch (index) {
			case VIEW_INDEX_LEFT:
				return views[index];
			case VIEW_INDEX_RIGHT:
				return views[index];
		}
		return null;
	}

	public int getCurrentFront() {
		return mCurrentFront;
	}

	private void processActionMove(MotionEvent event) {
		int deltaX = (int) event.getX() - downX;
		int offset = (int) event.getX() - lastPointX;
		lastPointX = (int) event.getX();
		switch (mCurrentFront) {
			case PAGE_LEFT:
				mCurrentMovedItemIndex = VIEW_INDEX_LEFT;
				dragLayout = getDragView(mCurrentMovedItemIndex);
				if (dragLayout != null) {
					if (deltaX > 0) {
						offset = dragLayout.getWidth() - dragLayout.getRight();
					}
					dragLayout.offsetLeftAndRight(offset);
					invalidate();
					checkToResetLastMovedView(mCurrentMovedItemIndex);
				}
				break;
			case PAGE_MIDDLE:
				if (deltaX > 0) {
					mCurrentMovedItemIndex = VIEW_INDEX_LEFT;
				} else if (deltaX < 0) {
					mCurrentMovedItemIndex = VIEW_INDEX_RIGHT;
				} else {
					break;
				}
				dragLayout = getDragView(mCurrentMovedItemIndex);
				if (dragLayout != null) {
					if (mCurrentMovedItemIndex == VIEW_INDEX_LEFT) {
						if (dragLayout.getRight() >= 0 && dragLayout.getRight() <= getWidth()) {
							dragLayout.offsetLeftAndRight(offset);
							invalidate();
						}
					} else if (mCurrentMovedItemIndex == VIEW_INDEX_RIGHT) {
						if (dragLayout.getLeft() >= 0 && dragLayout.getLeft() <= getWidth()) {
							dragLayout.offsetLeftAndRight(offset);
							invalidate();
						}
					}
				}
				checkToResetLastMovedView(mCurrentMovedItemIndex);
				break;
			case PAGE_RIGHT:
				mCurrentMovedItemIndex = VIEW_INDEX_RIGHT;
				dragLayout = getDragView(mCurrentMovedItemIndex);
				if (dragLayout != null) {
					if (deltaX < 0) {
						offset = dragLayout.getWidth() - dragLayout.getRight();
					}
					dragLayout.offsetLeftAndRight(offset);
					invalidate();
					checkToResetLastMovedView(mCurrentMovedItemIndex);
				}

				break;
		}
	}

	private void processActionUp(MotionEvent event) {
		final int pointerId = event.getPointerId(0);
		mVelocityTracker.computeCurrentVelocity(1000, MAX_VELOCITY);
		final float velocityX = mVelocityTracker.getXVelocity(pointerId);
		int offset = 0;
		switch (mCurrentMovedItemIndex) {
			case VIEW_INDEX_LEFT:
				if (dragLayout != null) {
					if (velocityX < 0 && Math.abs(velocityX) >= MIN_VELOCITY) {
						offset = 0 - dragLayout.getRight();
						mCurrentFront = PAGE_MIDDLE;
					} else if (velocityX > 0 && velocityX >= MIN_VELOCITY) {
						if (mCurrentFront == PAGE_MIDDLE) {
							if (listener != null) {
								listener.onPageSelected(PAGE_LEFT);
							}
						}
						offset = getWidth() - dragLayout.getRight();
						mCurrentFront = PAGE_LEFT;
					} else if (velocityX >= 0 && velocityX < MIN_VELOCITY || velocityX <= 0
							&& Math.abs(velocityX) < MIN_VELOCITY) {
						if (dragLayout.getRight() >= screenWidth / 2) {
							if (mCurrentFront == PAGE_MIDDLE) {
								if (listener != null) {
									listener.onPageSelected(PAGE_LEFT);
								}
							}
							offset = getWidth() - dragLayout.getRight();
							mCurrentFront = PAGE_LEFT;
						} else {
							offset = 0 - dragLayout.getRight();
							mCurrentFront = PAGE_MIDDLE;
						}
					}
				}
				break;
			case VIEW_INDEX_RIGHT:
				if (dragLayout != null) {
					if (velocityX < 0 && Math.abs(velocityX) >= MIN_VELOCITY) {
						if (mCurrentFront == PAGE_MIDDLE) {
							if (listener != null) {
								listener.onPageSelected(PAGE_RIGHT);
							}
						}
						offset = 0 - dragLayout.getLeft();
						mCurrentFront = PAGE_RIGHT;
					} else if (velocityX > 0 && velocityX >= MIN_VELOCITY) {
						offset = getWidth() - dragLayout.getLeft();
						mCurrentFront = PAGE_MIDDLE;
					} else if (velocityX >= 0 && velocityX < MIN_VELOCITY || velocityX <= 0
							&& Math.abs(velocityX) < MIN_VELOCITY) {
						if (dragLayout.getLeft() >= screenWidth / 2) {
							offset = getWidth() - dragLayout.getLeft();
							mCurrentFront = PAGE_MIDDLE;
						} else {
							if (mCurrentFront == PAGE_MIDDLE) {
								if (listener != null) {
									listener.onPageSelected(PAGE_RIGHT);
								}
							}
							offset = 0 - dragLayout.getLeft();
							mCurrentFront = PAGE_RIGHT;
						}
					}
				}
				break;
		}
		if (mCurrentMovedItemIndex != Integer.MIN_VALUE && dragLayout != null) {
			mScroller.startScroll(dragLayout.getLeft(), 0, offset, 0);
			invalidate();
			checkToResetLastMovedView(mCurrentMovedItemIndex);
		}
		if (event.getPointerCount() == 1) {
			mActivePointerId = INVALID_POINTER;
		}
	}

	public void scrollView(int viewIndex, boolean show) {
		int offset = 0;
		dragLayout = getDragView(viewIndex);
		switch (viewIndex) {
			case VIEW_INDEX_LEFT:
				if (dragLayout != null) {
					if (!show) {
						offset = 0 - dragLayout.getRight();
						mCurrentFront = PAGE_MIDDLE;
					} else {
						offset = getWidth() - dragLayout.getRight();
						mCurrentFront = PAGE_LEFT;
					}
				}
				break;
			case VIEW_INDEX_RIGHT:
				if (dragLayout != null) {
					if (show) {
						offset = 0 - dragLayout.getLeft();
						mCurrentFront = PAGE_RIGHT;
					} else {
						offset = getWidth() - dragLayout.getLeft();
						mCurrentFront = PAGE_MIDDLE;
					}
				}
				break;
		}
		mScroller.startScroll(dragLayout.getLeft(), 0, offset, 0);
		invalidate();
	}

	private void checkToResetLastMovedView(int currentMovedItemIndex) {
		if (mLastMoveItemIndex != Integer.MIN_VALUE && mLastMoveItemIndex != currentMovedItemIndex) {
			View targetView = views[mLastMoveItemIndex];
			if (targetView != null) {
				int targetOffset = (mLastMoveItemIndex - 1 == mCurrentFront) ? 0 : (mLastMoveItemIndex - 1)
						* targetView.getWidth();
				targetView.offsetLeftAndRight(targetOffset - targetView.getLeft());
			}
		}
		mLastMoveItemIndex = currentMovedItemIndex;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (dragLayout != null) {
				dragLayout.offsetLeftAndRight(mScroller.getCurrX() - dragLayout.getLeft());
			}
			postInvalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthSize, heightSize);
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child != null) {
				final int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
				final int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
				child.measure(contentWidthSpec, contentHeightSpec);
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int height = b - t;
		int width = r - l;
		if (mCurrentFront == PAGE_MIDDLE) {
			for (int i = 0; i < 3; ++i) {
				View viewItem = views[i];
				if (viewItem != null) {
					viewItem.layout(0 - width + i * width, 0, i * width, height);
				}
			}
		} else if (mCurrentFront == PAGE_LEFT) {
			for (int i = 0; i < PAGE_COUNT; i++) {
				View viewItem = views[i];
				if (viewItem != null) {
					if (i == VIEW_INDEX_MIDDLE) {
						viewItem.layout(0, 0, width, height);
					} else if (i == VIEW_INDEX_RIGHT) {
						viewItem.layout(width, 0, i * width, height);
					} else if (i == VIEW_INDEX_LEFT) {
						viewItem.layout(0, 0, width, height);
					}
				}
			}
		} else if (mCurrentFront == PAGE_RIGHT) {
			for (int i = 0; i < PAGE_COUNT; i++) {
				View viewItem = views[i];
				if (viewItem != null) {
					if (i == VIEW_INDEX_MIDDLE) {
						viewItem.layout(0, 0, width, height);
					} else if (i == VIEW_INDEX_RIGHT) {
						viewItem.layout(0, 0, width, height);
					} else if (i == VIEW_INDEX_LEFT) {
						viewItem.layout(0 - width, 0, 0, height);
					}
				}
			}
		}
	}

	private GestureDetector gestureDetector = new GestureDetector(getContext(), new InternalGestureDetector());

	public void setAllowSlide(boolean allowSlide) {
		this.allowSlide = allowSlide;
	}

	private class InternalGestureDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if ((Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > MIN_DISTANCE) || isDragging) {
				return true;
			}
			return false;
		}
	}

	public void setPageSelectedListener(PageSelectedListener listener) {
		this.listener = listener;
	}

	private PageSelectedListener listener;
	private static final int MIN_DISTANCE = 30;

	public interface PageSelectedListener {
		void onPageSelected(int currentPage);
	}

}

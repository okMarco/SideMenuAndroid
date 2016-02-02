package com.example.sidemenuandroid;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class CustomSideMenu extends RelativeLayout{
	
	public final static double SIDEMENUSCAL = 0.75;
	private Scroller mScroller;
	private int mWidthOfSideMenu;
	private int mEdge;
	private Point mInitPoint = new Point();
	private float iniEventX;
	private float iniEvnetY;
	private OnSideMenuListen mOnSideMenuListen;

	public CustomSideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onFinishInflate() {
		mScroller = new Scroller(getContext());
		super.onFinishInflate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		View child = (View) getChildAt(0);
		LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
		ScreenTools screenTools = ScreenTools.instance(getContext());
		layoutParams.width = mWidthOfSideMenu = (int) (screenTools.getScreenWidth() * SIDEMENUSCAL);
		layoutParams.height = screenTools.getScreenHeight();
		child.setLayoutParams(layoutParams);
		mEdge = screenTools.dip2px(15); 
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		View child = (View) getChildAt(0);
		child.layout(-child.getMeasuredWidth(), 0, 0, child.getMeasuredHeight());
		mInitPoint.x = child.getLeft();
		mInitPoint.y = child.getTop();
	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(mScroller.computeScrollOffset()){
			scrollTo(mScroller.getCurrX(), 0);
		    mOnSideMenuListen.setShadowAlpha(getShadowAlpha());
			invalidate();
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getX() < mEdge || getScrollX() < 0) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				iniEventX = event.getX();
				iniEvnetY = event.getY();
				return true;
		    case MotionEvent.ACTION_MOVE:
		    	float tmp = event.getX() - iniEventX;
				iniEventX = event.getX();
				if(getScrollX()-tmp < -mWidthOfSideMenu)
					scrollTo((int) (-mWidthOfSideMenu), 0);
				else 
				    scrollTo((int) (getScrollX()-tmp), 0);
				mOnSideMenuListen.setShadowAlpha(getShadowAlpha());
				break;
			case MotionEvent.ACTION_UP:
				int endX = (int) event.getX();
				int endY = (int) event.getY();
				if(endX == iniEventX && endY == iniEvnetY && iniEventX > mWidthOfSideMenu) {
					sideMenuScroll(false);
					break;
				}
				else {
					if(getScrollX() < (-mWidthOfSideMenu/2))
						mScroller.startScroll(getScrollX(), 0, (int) (-mWidthOfSideMenu-getScrollX()), 0, 500);
					else 
						mScroller.startScroll(getScrollX(), 0, (int) (0-getScrollX()), 0, 500);
					break;
				}
			default:
				break;
			}
			invalidate();
			return true;
		}
		return false;
	}
	
	public void sideMenuScroll(boolean isLeftToRight) {
		if(isLeftToRight) {
			mScroller.startScroll(0, 0, 0-mWidthOfSideMenu, 0, 1000);
		}
		else
			mScroller.startScroll(0-mWidthOfSideMenu, 0, mWidthOfSideMenu, 0, 1000);
		invalidate();
	}
	
	public void doScroll(int sx, int sy, int dx, int dy) {
		mScroller.startScroll(sx, sy, dx, dy, 500);
		invalidate();
	}
	
	public int getShadowAlpha() {
		int currentScroll = getScrollX();
		if(currentScroll > 0)
			currentScroll = 0;
		if(-currentScroll > mWidthOfSideMenu)
			currentScroll = -mWidthOfSideMenu;
		int alpha = (int) (-currentScroll*1.0/mWidthOfSideMenu * 100);
		return alpha;
	}
	
	public void setSideMenuListen(OnSideMenuListen onSideMenuListen){
		mOnSideMenuListen = onSideMenuListen;
	}
	
	public interface OnSideMenuListen{
		public void setShadowAlpha(int alpha);
	}
}

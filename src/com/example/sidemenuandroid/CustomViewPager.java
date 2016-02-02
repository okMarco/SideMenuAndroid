package com.example.sidemenuandroid;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager{

	private float mInitX;
	private float mInitY;
	private boolean mFirstTime;
	private boolean mSideMenuShow;
	private ViewPagerListener mViewPagerListener;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(arg0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mInitX = event.getX();
			mInitY = event.getY();
			mFirstTime = false;
			break;
        case MotionEvent.ACTION_MOVE:
        	if(mFirstTime) {
        		mInitX = event.getX();
    			mInitY = event.getY();
    			mFirstTime = false;
        	}
        	float tmpX = event.getX();
        	float tmpY = event.getY();
        	
            if((tmpX - mInitX) > 0 || mSideMenuShow){
            	if(Math.abs(tmpX - mInitX) > Math.abs(tmpY - mInitY) && getCurrentItem() == 0){
            		mSideMenuShow = true;
            		mViewPagerListener.setScroll((int) (mInitX - tmpX), false);
            	    mInitX = tmpX;
            	    mInitY = tmpY;
            	}
            }
            break;
        case MotionEvent.ACTION_UP:
        	mFirstTime = true;
        	if(mSideMenuShow){
        		mSideMenuShow = false;
                mViewPagerListener.setScroll(0, true);
        	}
		default:
			break;
		}
		if(!mSideMenuShow){
		return super.onTouchEvent(event);
		}
		else
			return true;
	}
	
	public void setViewPagerListener(ViewPagerListener viewPagerListener) {
		this.mViewPagerListener = viewPagerListener;
	}
	
	public interface ViewPagerListener {
		public void setScroll(int s, boolean isAction_UP);
	}
}

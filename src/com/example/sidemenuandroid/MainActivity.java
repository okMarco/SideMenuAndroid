package com.example.sidemenuandroid;

import java.util.ArrayList;

import com.example.sidemenuandroid.CustomSideMenu.OnSideMenuListen;
import com.example.sidemenuandroid.CustomViewPager.ViewPagerListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private View mShadow;
	private CustomSideMenu mCustomSideMenu;
	private CustomViewPager mCustomViewPager;
	private int mWidthOfSideMenu;
	private com.example.sidemenuandroid.ScreenTools mScreenTools;
	private ArrayList<View> mViewContainter;
	private ImageButton mBtnSideMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mShadow = (View) findViewById(R.id.shadow);
		mCustomSideMenu = (CustomSideMenu) findViewById(R.id.side_menu_layout);
		mCustomViewPager = (CustomViewPager) findViewById(R.id.viewpager);
		mBtnSideMenu = (ImageButton) findViewById(R.id.btn_side_menu);
		
		mShadow.getBackground().setAlpha(0);
		
		mShadow.bringToFront();
		mCustomSideMenu.bringToFront();

		mScreenTools = ScreenTools.instance(getApplicationContext());
	    mWidthOfSideMenu = (int) (mScreenTools.getScreenWidth() * CustomSideMenu.SIDEMENUSCAL);	
	    
	    mViewContainter = new ArrayList<View>();
	    View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.viewpager_view1, null);
	    View view2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.viewpager_view2, null);
	    View view3 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.viewpager_view3, null);
	    
	    mViewContainter.add(view1);
	    mViewContainter.add(view2);
	    mViewContainter.add(view3);
	    
	    mCustomViewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mViewContainter.size();
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				((ViewPager) container).addView(mViewContainter.get(position));
				return mViewContainter.get(position);
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				((ViewPager) container).removeView(mViewContainter.get(position));
			}
			
		});
		
		mCustomSideMenu.setSideMenuListen(new OnSideMenuListen() {
			
			@Override
			public void setShadowAlpha(int alpha) {
				// TODO Auto-generated method stub
				mShadow.getBackground().setAlpha(alpha);
			}
		});
		
        mCustomViewPager.setViewPagerListener(new ViewPagerListener() {
        	
        	@Override
			public void setScroll(int s, boolean isActionUp) {
				// TODO Auto-generated method stub
				if(isActionUp) {
					if(mCustomSideMenu.getScrollX() < -mWidthOfSideMenu/2)
						mCustomSideMenu.doScroll(mCustomSideMenu.getScrollX(), 0, -mWidthOfSideMenu-mCustomSideMenu.getScrollX(), 0);
					else 
						mCustomSideMenu.doScroll(mCustomSideMenu.getScrollX(), 0, -mCustomSideMenu.getScrollX(), 0);
				}
				else {
					if(mCustomSideMenu.getScrollX()+s < -mWidthOfSideMenu)
						mCustomSideMenu.scrollTo((int) (-mWidthOfSideMenu), 0);
					else 
					    mCustomSideMenu.scrollTo((int) (mCustomSideMenu.getScrollX()+s), 0);
					mShadow.getBackground().setAlpha(mCustomSideMenu.getShadowAlpha());
				}
				
			}
		});
        
        mBtnSideMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mShadow.setVisibility(View.VISIBLE);
				mCustomSideMenu.sideMenuScroll(true);
			}
		});
	}

}

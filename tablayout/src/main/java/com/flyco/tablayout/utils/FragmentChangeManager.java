package com.flyco.tablayout.utils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentChangeManager {

    private boolean isDebug = false;

    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /**
     * Fragment切换数组
     */
    private ArrayList<Fragment> mFragments;
    /**
     * 与fragment list对应的fragment tag list
     */
    private ArrayList<String> mTags;
    /**
     * 当前选中的Tab
     */
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        initFragments();
    }

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments, ArrayList<String> tags) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        this.mTags = tags;
        initFragments();
    }

    /**
     * 初始化fragments
     */
    private void initFragments() {
        if (null != mTags && mTags.size() == mFragments.size()) {
            for (int index = 0; index < mFragments.size(); index++) {
                Fragment fragment = mFragments.get(index);
                if (!fragment.isAdded()) {
                    String tag = mTags.get(index);
                    mFragmentManager.beginTransaction().add(mContainerViewId, fragment, tag).hide(fragment).commit();
                }
            }
        } else {
            for (Fragment fragment : mFragments) {
                mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment).commit();
            }
        }

        setFragments(0);
    }

    /**
     * 界面切换控制
     */
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            if (mFragmentManager.isStateSaved()) {
                log("host state is saved.");
                ft.commitNowAllowingStateLoss();
            } else {
                log("host state is not saved.");
                ft.commit();
            }
        }
        mCurrentTab = index;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }

    public void setDebug(boolean flag) {
        this.isDebug = flag;
    }

    private void log(String msg) {
        if (isDebug) {
            Log.d("FragmentChangeManager", msg);
        }
    }

}
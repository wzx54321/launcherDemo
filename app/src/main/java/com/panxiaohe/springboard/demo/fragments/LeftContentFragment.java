package com.panxiaohe.springboard.demo.fragments;

import android.os.Bundle;
import android.view.View;

import com.panxiaohe.springboard.demo.R;

import xin.framework.base.fragment.XinFragment;

/**
 * 右边边内容
 */
public class LeftContentFragment extends XinFragment {

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void bindDataFirst() {

    }

    @Override
    public void bindUI(View rootView) {
        setTitleViewPadding(rootView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_left_content;
    }

    @Override
    public Object newP() {
        return null;
    }
}

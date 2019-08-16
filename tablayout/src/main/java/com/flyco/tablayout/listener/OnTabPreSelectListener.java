package com.flyco.tablayout.listener;

/**
 * 是否需要消耗掉当前这一次点击事件
 *
 * @author tuzhao
 */
public interface OnTabPreSelectListener {

    /**
     * @param position 当前点击tab的位置
     * @return true 消耗掉了当前点击事件 不会进行fragment切换
     */
    boolean onTabPreSelect(int position);

}

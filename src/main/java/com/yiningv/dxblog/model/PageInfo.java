package com.yiningv.dxblog.model;

public class PageInfo {

    private int current;

    private int total;

    private boolean prev;

    private boolean next;

    public PageInfo(int current, int total) {
        this.current = current;
        this.total = total;
        this.prev = current > 1;
        this.next = current < total;
    }

    public int getCurrent() {
        return current;
    }

    public int getTotal() {
        return total;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }
}

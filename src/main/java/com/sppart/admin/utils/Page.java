package com.sppart.admin.utils;

public class Page {
    public static int[] getPageIndex(int page) {
        int start = (page-1) * 10;
        int end = page * 10;
        return new int[]{start, end};
    }
}

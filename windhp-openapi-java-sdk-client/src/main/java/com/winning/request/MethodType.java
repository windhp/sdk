package com.winning.request;

/**
 * @description: http方法
 * @author: xch
 * @time: 2021/12/21 10:23
 */
public enum MethodType {
    /**
     * GET
     */
    GET(false),

    /**
     * PUT
     */
    PUT(true),

    /**
     * POST
     */
    POST(true),

    /**
     * DELETE
     */
    DELETE(false),

    /**
     * HEAD
     */
    HEAD(false),

    /**
     * OPTIONS
     */
    OPTIONS(false);

    private boolean hasContent;

    MethodType(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public boolean hasContent() {
        return hasContent;
    }
}

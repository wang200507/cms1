package com.song.util;

import java.io.Serializable;


/**
 * 返回结果类
 */
public class DseResult implements Serializable {
    //状态
    private int status = 1; //默认 1:成功，0：失败 用户可以自定义
    //消息
    private String msg = "操作成功";
    //数据
    private Object data;

    public DseResult(int status, String msg) {
        this.status=status;
        this.msg=msg;
    }

    public DseResult(int status, String msg, Object data) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    public static DseResult success() {
        return new DseResult(1, "操作成功");
    }

    public static DseResult success(Object data) {
        return new DseResult( 1, "操作成功",data);
    }
    public static DseResult success(String msg) {
        return new DseResult(1, msg);
    }



    public static DseResult faild() {
        return new DseResult( 0, "操作失败");
    }
    public static DseResult faild(String msg) {
        return new DseResult( 0,msg);
    }
    public static DseResult faild(Object data) {
        return new DseResult( 0, "操作失败",data);
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}

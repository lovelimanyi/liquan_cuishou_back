
package com.info.back.vo.jxl;

/**
 * 用户申请表检测
 * @author yyf
 *
 * @version
 */

public class ApplicationCheck {
    //申请表数据点. 用户名，用户idCard，电话，家庭地址，contact，
    private String app_point;
    //数据检查点
    private CheckPoints check_points;

    public String getApp_point() {
        return app_point;
    }
    public void setApp_point(String app_point) {
        this.app_point = app_point;
    }
    public CheckPoints getCheck_points() {
        return check_points;
    }
    public void setCheck_points(CheckPoints check_points) {
        this.check_points = check_points;
    }

}


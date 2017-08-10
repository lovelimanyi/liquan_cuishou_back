package com.info.back.vo.jxl;
/**
 * 用户行为检测
 * @author yyf
 *
 * @version
 */
public class BehaviorCheck {
    //分析点
    private String check_point;
    //分析点中文
    private String check_point_cn;
    //检查结果
    private String result;
    //证据
    private String evidence;
    //标记 0:无数据  1：通过  2：不通过
    private Integer score;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getEvidence() {
        return evidence;
    }
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public String getCheck_point() {
        return check_point;
    }
    public void setCheck_point(String check_point) {
        this.check_point = check_point;
    }
    public String getCheck_point_cn() {
        return check_point_cn;
    }
    public void setCheck_point_cn(String check_point_cn) {
        this.check_point_cn = check_point_cn;
    }

}

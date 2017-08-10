package com.info.back.vo.jxl;
/**
 * 月互动详情
 * @author yyf
 *
 * @version
 */
public class ServiceDetails {
    //互动次数
    private Integer interact_cnt;
    //互动月份
    private String interact_mth;

    public Integer getInteract_cnt() {
        return interact_cnt;
    }

    public void setInteract_cnt(Integer interactCnt) {
        interact_cnt = interactCnt;
    }

    public String getInteract_mth() {
        return interact_mth;
    }

    public void setInteract_mth(String interactMth) {
        interact_mth = interactMth;
    }

    @Override
    public String toString() {
        return "("+interact_mth+","+interact_cnt+")";
    }

}

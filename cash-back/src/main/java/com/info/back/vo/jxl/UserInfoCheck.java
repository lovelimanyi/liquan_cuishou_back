package com.info.back.vo.jxl;

/**
 * 用户信息检测
 * @author yyf
 *
 * @version
 */
public class UserInfoCheck {

    //用户黑名单信息
    private CheckBlackInfo check_black_info;
    //用户查询信息新
    private CheckSearchInfo check_search_info;

    public CheckBlackInfo getCheck_black_info() {
        return check_black_info;
    }

    public void setCheck_black_info(CheckBlackInfo checkBlackInfo) {
        check_black_info = checkBlackInfo;
    }

    public CheckSearchInfo getCheck_search_info() {
        return check_search_info;
    }

    public void setCheck_search_info(CheckSearchInfo checkSearchInfo) {
        check_search_info = checkSearchInfo;
    }

}

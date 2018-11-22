package com.info.constant;


public class Constant {

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD = "DEFAULT_PWD";
    /**
     * 下载模板默认密码
     */
    public static final String EXCEL_PWD = "EXCEL_PWD";
    /**
     * 命名空间编码
     */
    public static final String NAME_SPACE = "nameSpace";
    /*** UTF-8编码 */
    public static final String UTF8 = "UTF-8";
    /**
     * 后台请求路径
     */
    public final static String BACK_URL = "BACK_URL";
    /**
     * 后台session名
     */
    public static final String BACK_USER = "BACK_USER";
    /**
     * 后台超级管理员主键ID，该用户不用进行权限判断
     */
    public static final Integer ADMINISTRATOR_ID = 10000;
    /**
     * 后台催收经理主键ID 该用户不用进行权限判断
     **/
    public static final String ROLE_ID = "10019";
    /**
     * 当前页数
     */
    public final static String CURRENT_PAGE = "pageNum";
    /**
     * 每页显示多少条
     */
    public static final String PAGE_SIZE = "numPerPage";
    /**
     * 系统参数中返回list时使用的key的后缀
     */
    public static final String SYS_CONFIG_LIST = "_LIST";
    /**
     * 判断是否是http开头
     */
    public static final String HTTP = "http://";
    /**
     * 判断是否是https开头
     */
    public static final String HTTPS = "https://";

    /**
     * 前台用户缓存
     */
    public static final String FRONT_USER = "FRONT_USER";

    public static final String CACHE_INDEX_KEY = "ZHONGBAO_INDEX";// 缓存名称

    /**
     * 绑定手机号的验证码
     */
    public static final String SMS_BIND_PHONE = "SMS_BIND_PHONE";
    /**
     * 找回密码的验证码
     */
    public static final String SMS_RESET_PWD = "SMS_RESET_PWD";
    /**
     * 注册的验证码
     */
    public static final String SMS_REGISTER = "SMS_REGISTER";
    /**
     * 解绑银行卡的验证码
     */
    public static final String SMS_DEL_BANK = "SMS_DEL_BANK";
    /**
     * 设置交易密码的验证码
     */
    public static final String SMS_SET_PAY = "SMS_SET_PAY";
    /**
     * 修改手机号的时候原手机号的验证码
     */
    public static final String SMS_SET_PHONE_OLD = "SMS_SET_PHONE_OLD";
    /**
     * 修改手机号的时候新手机号的验证码
     */
    public static final String SMS_SET_PHONE_NEW = "SMS_SET_PHONE_NEW";
    /**
     * 前台用户发布需求的缓存标识
     */
    public static final String PUB_INFO = "PUB_NES_";
    /**
     * 前台用户信息点赞的缓存标识
     */
    public static final String PUB_INFO_LIKE = "PUB_NES_LIKE_";
    /*********************************************/
    /**
     * 短信类型：验证码类
     */
    public static final String VERIFY_CODE = "verify_code";
    /**
     * 短信类型：通知类类
     */
    public static final String NOTICE = "notice";
    /**
     * 短信类型：营销类
     */
    public static final String ADVERT = "advert";
    /**
     * 审核状态类型status    0 审核中，2审核通过 3，拒绝 ,4失效 5,通过不计入考核  6处理中
     */
    public static final String AUDIT_CHECKING = "0";
    public static final String AUDIT_PASS = "2";
    public static final String AUDIT_REFUSE = "3";
    public static final String AUDIT_INVALID = "4";
    public static final String AUDIT_PASS_NO_CHECK = "5";
    public static final String AUDIT_DOING = "6";
    /**
     * 审核type  1:聚信立，2 催收建议 ,3 减免 , 4 催收详情
     */
    public static final String AUDIT_TYPE_JXL = "1";
    public static final String AUDIT_TYPE_ADVICE = "2";
    public static final String AUDIT_TYPE_REDUCTION = "3";
    public static final String AUDIT_TYPE_DETAIL = "4";


    /**
     * 同步类型

     */
    /**
     * 电销未还款redis-key
     */
    public static final String DX_NOPAY = "dx:unrepay:";
    /**
     * 电销已还款redis-key
     */
    public static final String DX_PAY = "dx:repay:";
    /**
     * 逾期
     */
    public static final String TYPE_OVERDUE = "OVERDUE";
    /**
     * 续期
     */
    public static final String TYPE_RENEWAL = "RENEWAL";
    /**
     * 还款
     */
    public static final String TYPE_REPAY = "REPAY";
    /**
     * 代扣 redis-key
     */
    public static final String TYPE_WITHHOLD = "WITHHOLD";
    /**
     * 逾期redis-key
     */
    public static final String TYPE_OVERDUE_ = "OVERDUE_";
    /**
     * 续期 redis-key
     */
    public static final String TYPE_RENEWAL_ = "RENEWAL_";
    /**
     * 还款 redis-key
     */
    public static final String TYPE_REPAY_ = "REPAY_";
    /**
     * 代扣 redis-key
     */
    public static final String TYPE_WITHHOLD_ = "WITHHOLD_";
    /**
     * 借款状态（3:续期''4''-逾期,''5''-还款结束）            order: 4:催收成功 5：续期  0 催收成功
     */
    public static final String STATUS_OVERDUE_THREE = "3";

    public static final String STATUS_OVERDUE_FOUR = "4";

    public static final String STATUS_OVERDUE_FIVE = "5";

    public static final String STATUS_OVERDUE_TWO = "2";

    public static final String STATUS_OVERDUE_ONE = "1";

    public static final String STATUS_OVERDUE_ZERO = "0";
    /**
     * 借款状态  订单表的status    0待催收  1催收中  2承诺还款  3待催收(委外) 4催收完成  5续期  6减免审核中  7减免审核通过  8减免审核拒绝
     */
    public static final String STATUS_OVERDUE_SIX = "6";
    public static final String STATUS_OVERDUE_SEVEN = "7";
    public static final String STATUS_OVERDUE_EIGHT = "8";

    public static final String OPERATOR_NAME = "系统";

    public static final String PAY_MENT_SUCCESS = "还款成功催收员:";

    public static final int CREDITLOANPAY_OVERDUEA = 3;// 逾期1到10天S1
    public static final int CREDITLOANPAY_OVERDUEB = 4;// 逾期11到30天S2
    public static final int CREDITLOANPAY_OVERDUEC = 5;// 逾期31到60天M1-M2
    public static final int CREDITLOANPAY_OVERDUED = 6;// 逾期61到90天M2-M3
    public static final int CREDITLOANPAY_OVERDUEE = 7;// 逾期大于90天 M3+
    public static final int CREDITLOANPAY_OVERDUE_UNCOMPLETE = 8;// 续期（该状态催收员不能操作）
    public static final int CREDITLOANPAY_COMPLETE = 2;// 已还款

    //极速现金侠借、还款状态
    /**
     * 还款中【续期】（催收遇到此状态说明在极速现金侠后台已置为续期）
     */
    public static final String STATUS_HKZ = "21";
    /**
     * 已逾期
     */
    public static final String STATUS_YYQ = "-11";
    /**
     * 已坏账
     */
    public static final String STATUS_YHZ = "-20";
    /**
     * 已还款
     */
    public static final String STATUS_YHK = "30";
    /**
     * 逾期已还款
     */
    public static final String STATUS_YQYHK = "34";

    public static final String S_FLAG = "S1";

    /**
     * 聚信立类型
     */
    public static final String JXL_DETAIL = "jxl_detail"; //聚信立
    public static final String JDQ_DETAIL = "jdq_detail"; //借点钱
    public static final String R360_DETAIL = "r360_detail"; //融360
    public static final String JLM_DEATIL = "jlm_detail"; //借了吗
    public static final String FQGJ_DETAIL = "fqgj_detail"; //分期管家
    public static final String DK360_DETAIL = "dk360_detail"; //360贷款
    public static final String RS_DETAIL = "rs_detail"; //榕树
    public static final String LF_DETAIL = "lf_detail";//来福贷款王渠道

    /**
     * 大额，小额常量
     */
    public static final String BIG = "1";//大额
    public static final String SMALL = "2";//小额
    public static final String FEN = "3";//分期商城（商品分期）

    /**
     * 催收代扣队列名----小额
     */
    public static final String CUISHOU_WITHHOLD_QUEUE = "cuishou_withhold_queue";
    /**
     * 催收代扣队列名----大额
     */
    public static final String CUISHOU_WITHHOLD_QUEUE_BIG = "collection_withhold_push_queue";
    /**
     * 催收减免队列----大额
     */
    public static final String CUISHOU_BIG_REDUCTION_QUEUE="collection_deduct_push_queue";

    /**
     * 区分大小额订单标识符（大额订单借款id、还款id皆带有此标识）
     */
    public static final String SEPARATOR_FOR_ORDER_SOURCE = "-";

    /**
     * 减免订单类型  1大额  2小额  3分期商城
     */
    public static final String ORDER_TYPE_BIG = "1";
    public static final String ORDER_TYPE_SMALL = "2";
    public static final String ORDER_TYPE_FEN = "3";

}

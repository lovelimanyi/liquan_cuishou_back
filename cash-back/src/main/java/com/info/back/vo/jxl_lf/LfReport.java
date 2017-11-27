package com.info.back.vo.jxl_lf;

import java.util.List;

/**
 * 类描述：来福聚信立报告
 * 创建人：yyf
 * 创建时间：2017/11/24 0024上午 11:32
 */

public class LfReport {
    private CarrierMobileBasic carrier_mobile_basic;

    private List<ArrierMobileVoiceCall> carrier_mobile_voice_call;

    public CarrierMobileBasic getCarrier_mobile_basic() {
        return carrier_mobile_basic;
    }

    public void setCarrier_mobile_basic(CarrierMobileBasic carrier_mobile_basic) {
        this.carrier_mobile_basic = carrier_mobile_basic;
    }

    public List<ArrierMobileVoiceCall> getCarrier_mobile_voice_call() {
        return carrier_mobile_voice_call;
    }

    public void setCarrier_mobile_voice_call(List<ArrierMobileVoiceCall> carrier_mobile_voice_call) {
        this.carrier_mobile_voice_call = carrier_mobile_voice_call;
    }
}

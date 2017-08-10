package com.info.back.vo.jxl;
/**
 * 出行分析
 * @author yyf
 *
 * @version
 */
public class TripInfo {
    //出发地
    private String trip_leave;
    //目的地
    private String trip_dest;
    //出行时间类型
    private String trip_type;
    //出行开始时间
    private String trip_start_time;
    //出行结束时间
    private String trip_end_time;

    public String getTrip_leave() {
        return trip_leave;
    }
    public void setTrip_leave(String trip_leave) {
        this.trip_leave = trip_leave;
    }
    public String getTrip_dest() {
        return trip_dest;
    }
    public void setTrip_dest(String trip_dest) {
        this.trip_dest = trip_dest;
    }
    public String getTrip_type() {
        return trip_type;
    }
    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }
    public String getTrip_start_time() {
        return trip_start_time;
    }
    public void setTrip_start_time(String trip_start_time) {
        this.trip_start_time = trip_start_time;
    }
    public String getTrip_end_time() {
        return trip_end_time;
    }
    public void setTrip_end_time(String trip_end_time) {
        this.trip_end_time = trip_end_time;
    }




}

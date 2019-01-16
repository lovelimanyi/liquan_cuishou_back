package com.info.back.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: cff
 * Date: 2018-05-23
 * Time: 下午 2:17
 */
public class BizException extends Exception {

    public BizException(){
        super();
    }

    public BizException(String message){
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}

package cn.edu.tju.entity;

import lombok.Data;

/**
 */
@Data
public class ResponseData<T>{

    //成功
    public final static int SUCCESS = 0;

    //失败
    public final static int ERROR = 1;

    private Integer code;

    private String message;

    private T data;

    public ResponseData() {
    }

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public ResponseData(boolean code, String message) {
        this.code = code?0:-1;
        this.message=code?"成功":"失败";
        this.message = message+this.message;
    }

    public ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

package cn.edu.tju.entity;

import lombok.Data;

import java.util.List;

@Data
public class JsonResult<T> {
    //成功
    public final static int SUCCESS = 0;
    //失败
    public final static int ERROR = -1;
    private Integer code;
    private String msg;
    private Long count;
    private List<T> data;

    public JsonResult(Long count, List<T> data) {
        this.count = count;
        this.data = data;
        this.code = SUCCESS;
    }
}

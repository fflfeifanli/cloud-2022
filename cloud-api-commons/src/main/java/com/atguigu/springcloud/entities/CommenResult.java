package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommenResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    public CommenResult(Integer code,String message){
        this(code,message,null);
    }
}

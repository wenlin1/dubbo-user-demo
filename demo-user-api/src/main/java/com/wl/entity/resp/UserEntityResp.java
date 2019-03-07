package com.wl.entity.resp;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wl
 * @date 2019/2/27
 * 用户返回实体
 */
@Data
public class UserEntityResp  implements Serializable{
    private static final long serialVersionUID = 2L;
    /**用户id */
    private String userId;
    /**用户姓名*/
    private String UserName;
    /**年龄*/
    private int age;
    /**性别*/
    private String sex;


}

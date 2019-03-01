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
@ToString(exclude="serialVersionUID")
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
    /**邮箱*/
    private String email;
    /**电话号码*/
    private String phoneNumber;
    /**身份证*/
    private String useriDentification;
    /**地址*/
    private String userAdress;
    /** 省*/
    private String UserProvince;


}

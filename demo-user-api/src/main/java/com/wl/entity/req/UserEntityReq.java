package com.wl.entity.req;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

/**
 *@author wl
 *@date 2019/2/27
 * 用户请求实体类
 * 引入lombok 减少代码量
 * @Data 注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 * @NoArgsConstructor 注解在类上；为类提供一个无参的构造方法
 * @AllArgsConstructor 注解在类上；为类提供一个全参的构造方法
 * @ToString(exclude="serialVersionUID") toString 方法 去掉serialVersionUID 字段
 */
@Data
@ToString(exclude="serialVersionUID")
public class UserEntityReq implements Serializable{
    private static final long serialVersionUID = 1L;
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


}

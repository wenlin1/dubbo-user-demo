package com.wl.entity.service;

import com.wl.entity.req.UserEntityReq;
import com.wl.entity.resp.UserEntityResp;

import java.util.List;

/**
 * @author wl
 * @date 2019/2/27
 * 用户操作接口
 */
public interface UserService {

    /**
     * 保存用户信息
     * @param userEntityReq
     * @return
     */
    public boolean saveUser(UserEntityReq userEntityReq);

    /**
     * 修改用户信息
     * @param userEntityReq
     * @return
     */
    public boolean updateUser(UserEntityReq userEntityReq);

    /**
     * 获取单条用户信息
     * @param userReq
     * @return
     */
    public UserEntityResp getUser(UserEntityReq userReq);

    /**
     * 获取多条用户信息
     * @param userReq
     * @return
     */
    public List<UserEntityResp> getListUser(UserEntityReq userReq);

}

package com.wl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.wl.base.common.Log;
import com.wl.dao.UserInfoDao;
import com.wl.entity.req.UserEntityReq;
import com.wl.entity.resp.UserEntityResp;
import com.wl.entity.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户操作接口实现service
 * @author WL
 * @date 2019/3/8
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Log logger = new Log(UserServiceImpl.class);
    private static final String busName="UserServiceImpl";
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public boolean saveUser(UserEntityReq userEntityReq) {
        logger.reqPrint(busName, JSON.toJSONString(userEntityReq));
        UserEntityResp userEntityResp=(UserEntityResp)userInfoDao.save(userEntityReq);
        logger.info(userEntityResp.toString());
        return (userEntityResp==null)?false:true;
    }

    @Override
    public boolean updateUser(UserEntityReq userEntityReq) {
        return false;
    }

    @Override
    public UserEntityResp getUser(UserEntityReq userReq) {
        return null;
    }

    @Override
    public List<UserEntityResp> getListUser(UserEntityReq userReq) {
        return null;
    }
}

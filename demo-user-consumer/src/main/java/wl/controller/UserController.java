package wl.controller;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wl.entity.req.UserEntityReq;
import com.wl.entity.resp.UserEntityResp;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wl.common.ResBody;
import wl.common.log.Log;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

import static wl.common.log.Log.DEMO_USER_CONSUMER;

@Component
@RequestMapping(value="/demo/user")
public class UserController {
    private static final Log log=new Log(UserController.class);
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    @RequestMapping(value="/getUserInfo", method= {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResBody<UserEntityResp> getUserInfo(@Context HttpServletRequest request, String data){
        //打印请求日志
        log.reqPrint(DEMO_USER_CONSUMER,data);
        //将请求体转化为实体
        UserEntityReq userEntityReq = JSONObject.parseObject(data, UserEntityReq.class);
        //log.info(userEntityReq);
        //创建返回
        ResBody<UserEntityResp> resBody = new  ResBody<UserEntityResp>();
            UserEntityResp ss=new UserEntityResp();
            ss.setAge(11);
            ss.setSex("nan");
            ss.setUserId("1");
            ss.setUserName("nihj");
            resBody.setRetCode("00000");
            resBody.setRetDesc("成功");
            resBody.setRspBody(ss);
        log.respPrint(DEMO_USER_CONSUMER,JSON.toJSONString(resBody));
        return resBody;

    }


}

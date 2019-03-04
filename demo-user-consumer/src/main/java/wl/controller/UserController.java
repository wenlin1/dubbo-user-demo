package wl.controller;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.wl.entity.resp.UserEntityResp;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wl.common.ResBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

@Component
@RequestMapping(value="/demo/user")
public class UserController {
    @Consumes(ContentType.APPLICATION_JSON_UTF_8)
    @Produces(ContentType.APPLICATION_JSON_UTF_8)
    @RequestMapping(value="/getUserInfo", method= {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResBody<UserEntityResp> getUserInfo(@Context HttpServletRequest request, String data){
        // UserEntityReq userEntityReq = JSONObject.parseObject(data, UserEntityReq.class);
         ResBody<UserEntityResp> resBody = new  ResBody<UserEntityResp>();
         //System.out.println(JSON.toJSONString(userEntityReq));
        UserEntityResp ss=new UserEntityResp();
        ss.setAge(11);
        ss.setSex("nan");
        ss.setUserId("1");
        ss.setUserName("nihj");
        resBody.setRetCode("00000");
        resBody.setRetDesc("成功");
        resBody.setRspBody(ss);
        System.out.println(JSONObject.toJSONString(resBody));
        return resBody;

    }


}

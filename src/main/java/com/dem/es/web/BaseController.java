package com.dem.es.web;

import com.dem.es.entity.dto.LoginDTO;
import com.dem.es.exception.LoginException;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public Long getCurUserId() {
        LoginDTO userInfo = (LoginDTO) SecurityUtils.getSubject().getPrincipal();
        if(userInfo==null){
            throw new LoginException();
        }
        return userInfo.getUserId();
    }

    public String getCurUserName() {
        LoginDTO userInfo = (LoginDTO)SecurityUtils.getSubject().getPrincipal();
        return userInfo!=null?userInfo.getUserName():"";
    }
}

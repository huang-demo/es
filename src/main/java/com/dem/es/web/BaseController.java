package com.dem.es.web;

import com.dem.es.entity.dto.LoginDTO;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public Long getCurUserId() {
        LoginDTO userInfo = (LoginDTO) SecurityUtils.getSubject().getPrincipal();
        return userInfo!=null?userInfo.getUserId():0L;
    }

    public String getCurUserName() {
        LoginDTO userInfo = (LoginDTO)SecurityUtils.getSubject().getPrincipal();
        return userInfo!=null?userInfo.getUserName():"";
    }
}

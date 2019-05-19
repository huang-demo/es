package com.dem.es.web;

import com.dem.es.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
@Controller
public class LoginController {
    @GetMapping(value="login")
    public String login() {
        return "login";
    }

    @GetMapping(value="403")
    public String noPermissions() {
        return "403";
    }


    /**
     * ajax登录请求
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value="ajaxLogin")
    @ResponseBody
    public Result submitLogin(String username, String password) {
//        if(vcode==null||vcode==""){
//            return Result.error("验证码不能为空！");
//        }

        //转化成小写字母
        //还可以读取一次后把验证码清空，这样每次登录都必须获取验证码
        Session session = SecurityUtils.getSubject().getSession();
        //session.removeAttribute("_come");
//        if(!vcode.equals(v)){
//            return Result.error("验证码错误！");
//        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            return Result.success("登录成功");

        } catch (Exception e) {
           return Result.error(e.getMessage());
        }
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value="logout",method =RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> logout(){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            //退出
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultMap;
    }

}

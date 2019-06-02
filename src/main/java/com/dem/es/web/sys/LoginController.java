package com.dem.es.web.sys;

import com.dem.es.util.Result;
import com.dem.es.util.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
@Controller
public class LoginController {
    @Autowired
    private Producer producer;


    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response)throws IOException{
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @GetMapping(value="403")
    public String noPermissions() {
        return "/error/403";
    }


    /**
     * ajax登录请求
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value="/sys/login")
    @ResponseBody
    public Result submitLogin(String username,String password,String captcha) {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!captcha.equalsIgnoreCase(kaptcha)){
            return Result.error("验证码不正确");
        }
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
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }
}

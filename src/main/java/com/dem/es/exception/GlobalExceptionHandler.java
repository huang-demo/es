package com.dem.es.exception;

import com.dem.es.util.ErrorInfo;
import com.dem.es.util.GsonUtils;
import com.dem.es.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 全局错误处理
 * author Mr.p
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    private static String PUBLIC_ERROR_PAGE = "/error/404";

    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(HttpServletRequest req,Exception e) throws Exception{
        Map<String,String[]> parameterMap = req.getParameterMap();
        log.error("请求异常url:{},param:{},userName:{},err:{}",req.getRequestURI(),GsonUtils.obj2Json(parameterMap),"",e.getMessage());
        return Result.error("系统开小差");
    }

    /**
     * 捕获自定义异常错误
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req,MyException e) throws Exception{
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage("" + e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("返回json出错");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo<String> argumentErr(HttpServletRequest req,IllegalArgumentException e) throws Exception{
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage("" + e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("jap查询参数不能为空");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}

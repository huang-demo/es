package com.dem.es.exception;

import com.dem.es.util.ErrorInfo;
import com.dem.es.util.GsonUtils;
import com.dem.es.util.Result;
import com.dem.es.util.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * 全局错误处理
 * author Mr.p
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error("请求异常url:{},参数 {}, 当前操作用户 {},错误信息 {}", req.getRequestURI(), getRequestParamStr(req), ShiroUtils.getCurUserName(), e.getMessage());
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
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage("" + e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("返回json出错");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo<String> argumentErr(HttpServletRequest req, IllegalArgumentException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage("" + e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("jap查询参数不能为空");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }

    private String getRequestParamStr(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();

        StringBuilder sb = new StringBuilder(200);
        for (Iterator<Map.Entry<String, String[]>> iter = map.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<String, String[]> element = (Map.Entry<String, String[]>) iter.next();
            // 获取key值
            String strKey = element.getKey();
            // 获取value,默认为数组形式
            String[] value = element.getValue();
            sb.append(strKey).append(":");
            // 存放到指定的map集合中
            if (value.length == 1) {
                // 没有或者只有一个value值
                sb.append(value[0]);
            } else {
                sb.append(GsonUtils.obj2Json(value));
            }
            sb.append(",");
        }

        return sb.toString();
    }
}

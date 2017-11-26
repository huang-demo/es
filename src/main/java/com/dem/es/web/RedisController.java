package com.dem.es.web;

import com.dem.es.service.JedisClient;
import com.dem.es.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@Api(description = "Jedis 测试")
public class RedisController {
    @Autowired
    private JedisClient jedisClient;

    @ApiOperation(value = "redis新增key-value")
    @PostMapping("/redis")
    @ResponseBody
    public Result<String> redisSet(String key, String value) {
        String str = jedisClient.set(key, value);
        return Result.success(str);
    }

    @ApiOperation(value = "从redis中获取hash")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", paramType = "String", value = "key值"), @ApiImplicitParam(name = "item", paramType = "String")})
    @GetMapping("/redis/hget")
    @ResponseBody
    public Result<Object> hget(String key, String item) {
        String str = jedisClient.hget(key, item);
        return Result.success(str);
    }
}

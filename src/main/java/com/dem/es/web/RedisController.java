package com.dem.es.web;

import com.dem.es.service.JedisClient;
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
    public String redisSet(String key, String value) {
        jedisClient.set(key, value);
        return "SUCCESS";
    }

    @ApiOperation(value = "从redis中获取hash")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", paramType = "String", value = "key值"), @ApiImplicitParam(name = "item", paramType = "String")})
    @GetMapping("/redis/hget")
    @ResponseBody
    public String hget(String key, String item) {
        return jedisClient.hget(key, item);
    }
}

package com.dem.es.web.sys;

import com.dem.es.entity.vo.MenuVO;
import com.dem.es.service.SysMenuService;
import com.dem.es.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/menu")
@Api("系统菜单控制层")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("list")
    @ApiOperation(value = "获取菜单列表", notes = "获取菜单列表", response = MenuVO.class)
    public Result<MenuVO> getMenu() {

        return Result.success(sysMenuService.getAllMenu());
    }
}

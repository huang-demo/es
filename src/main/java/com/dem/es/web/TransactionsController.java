package com.dem.es.web;

import com.dem.es.service.TransactionsService;
import com.dem.es.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@Api("")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/aggColor/{size}")
    @ApiOperation(value = "按颜色统计个数", response = Result.class)
    public Result getCountByColor(@PathVariable Integer size) {
        return Result.success(transactionsService.getCountsByColor(size));
    }

    @GetMapping("/getColorsMinMax/{size}")
    public Result getMinMaxPrice(@PathVariable Integer size) {
//        return Result.success(transactionsService.getMinMaxPriceByColor(size));
        return Result.success(transactionsService.getStatasByColors(size));
    }

    @GetMapping("/getFordAvgAndGlobelAvg")
    public Result getFordAvgAndGlobelAvg() {
        return Result.success(transactionsService.getFordAvgAndGlobelAvg());
    }

    @GetMapping("/getAvg/{size}")
    @ApiOperation(value = "获取各产商的平均售价", response = Result.class)
    public Result getAvgPrice(@PathVariable Integer size) {
        return Result.success(transactionsService.getAvgPriceByMake(size));
    }

    @GetMapping("/getAvgAndMinPriceByColorAndMake/{size}")
    @ApiOperation(value = "按颜色统计每个汽车生成商计算最低和最高的价格", response = Result.class)
    public Result getAvgAndMinPriceByColorAndMake(@PathVariable Integer size) {
        return Result.success(transactionsService.getAvgAndMinPriceByColorAndMake(size));
    }

    @GetMapping("/getAmountByMonth")
    @ApiOperation(value = "按月统计总和", response = Result.class)
    public Result getAmountByMonth() {
        return Result.success(transactionsService.getAmountByMonth());
    }

    @GetMapping("/getPriceHistogram")
    @ApiOperation(value = "统计各价格区间总数", response = Result.class)
    public Result getPriceHistogram() {
        return Result.success(transactionsService.getPriceHistogram(20000));
    }

    @GetMapping("/globalAvg")
    public Result globalAvg(double minPrice) {
        return Result.success(transactionsService.getGlobalAvg(minPrice));
    }
    @GetMapping("/getFordAvg")
    public Result getFordAvg(double minPrice){
        return Result.success(transactionsService.getFordAndAvg(minPrice));
    }

}

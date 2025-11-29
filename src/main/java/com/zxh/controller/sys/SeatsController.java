package com.zxh.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Rooms;
import com.zxh.entity.Seats;
import com.zxh.service.impl.SeatsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 座位 前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@RestController
@RequestMapping("/seats")
@Api(tags = "座位管理")
public class SeatsController {
    @Autowired
    private SeatsServiceImpl seatsService;

    @ApiOperation("查列表")
    @GetMapping("/list")
    public AjaxResult list(Seats seats, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Seats> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Seats> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (seats != null) {
            //查询条件
        }
        Page<Seats> pages = seatsService.page(page, queryWrapper);
        long count = seatsService.count(queryWrapper);
        pages.setTotal(count);
        return AjaxResult.success(pages);
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Seats seats){
        if(seatsService.save(seats)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("添加失败！");
        }
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Seats seats){
        if(seatsService.updateById(seats)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("修改失败!");
        }
    }

    @ApiOperation("单查")
    @GetMapping("/selectById/{id}")
    public AjaxResult selectById(@PathVariable Integer id){
        Seats seats = seatsService.getById(id);
        return AjaxResult.success(seats);
    }

    @ApiOperation("单删")
    @DeleteMapping("/deleteByid/{id}")
    public AjaxResult deleteByid(@PathVariable Integer id){
        if (seatsService.removeById(id)) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error("删除失败！");
        }
    }

    @ApiOperation("多删")
    @DeleteMapping("/deleteByIds")
    public AjaxResult deleteByIds(@RequestBody List<Integer> ids){
        if (seatsService.removeByIds(ids)) {
            return AjaxResult.success();
        }else{
            return AjaxResult.error("删除失败!");
        }
    }
}

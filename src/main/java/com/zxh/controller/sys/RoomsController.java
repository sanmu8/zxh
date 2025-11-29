package com.zxh.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Rooms;
import com.zxh.service.impl.RoomsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 自习室 前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@RestController
@RequestMapping("/rooms")
public class RoomsController {
    @Autowired
    private RoomsServiceImpl roomsService;

    @ApiOperation("查列表")
    @GetMapping("/list")
    public AjaxResult list(Rooms rooms, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Rooms> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Rooms> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (rooms != null) {
            //查询条件
        }
        Page<Rooms> pages = roomsService.page(page, queryWrapper);
        long count = roomsService.count(queryWrapper);
        pages.setTotal(count);
        return AjaxResult.success(pages);
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Rooms rooms){
        QueryWrapper<Rooms> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("room_name", rooms.getRoomName());
        if (roomsService.getOne(QueryWrapper) != null){
            return AjaxResult.error("名称重复");
        }
        if(roomsService.save(rooms)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("添加失败！");
        }
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Rooms rooms){
        if(roomsService.updateById(rooms)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("修改失败!");
        }
    }

    @ApiOperation("单查")
    @GetMapping("/selectById/{id}")
    public AjaxResult selectById(@PathVariable Integer id){
        Rooms rooms = roomsService.getById(id);
        return AjaxResult.success(rooms);
    }

    @ApiOperation("单删")
    @DeleteMapping("/deleteByid/{id}")
    public AjaxResult deleteByid(@PathVariable Integer id){
        if (roomsService.removeById(id)) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error("删除失败！");
        }
    }

    @ApiOperation("多删")
    @DeleteMapping("/deleteByIds")
    public AjaxResult deleteByIds(@RequestBody List<Integer> ids){
        if (roomsService.removeByIds(ids)) {
            return AjaxResult.success();
        }else{
            return AjaxResult.error("删除失败!");
        }
    }
}

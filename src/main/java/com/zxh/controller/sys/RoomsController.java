package com.zxh.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Buildings;
import com.zxh.entity.Rooms;
import com.zxh.entity.Seats;
import com.zxh.service.impl.BuildingsServiceImpl;
import com.zxh.service.impl.RoomsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 自习室 前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@RestController
@RequestMapping("/sys/rooms")
@Api(tags = "自习室管理")
public class RoomsController {
    @Autowired
    private RoomsServiceImpl roomsService;

    @Autowired
    private BuildingsServiceImpl buildingsService;

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

        Set<Integer> ids = pages.getRecords().stream()
                .map(Rooms::getBuildingId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (!ids.isEmpty()) {
            Map<Integer, Buildings> map = buildingsService.listByIds(ids)
                    .stream()
                    .collect(Collectors.toMap(Buildings::getId, Function.identity()));

            pages.getRecords().forEach(e -> {
                Buildings buildings = map.get(e.getBuildingId());
                if (buildings != null) {
                    e.setBuildingName(buildings.getBuildingName());
                }
            });
        }

        return AjaxResult.success(pages);
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Rooms rooms){
//        QueryWrapper<Rooms> QueryWrapper = new QueryWrapper<>();
//        QueryWrapper.eq("room_name", rooms.getRoomName());
//        if (roomsService.getOne(QueryWrapper) != null){
//            return AjaxResult.error("名称重复");
//        }
//        if (rooms.getBuildingId() == null){
//            return AjaxResult.error("请选择所属楼栋");
//        }
        AjaxResult validationResult = validateRooms(rooms);
        if (validationResult != null) {
            return validationResult;
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
//        QueryWrapper<Rooms> QueryWrapper = new QueryWrapper<>();
//        QueryWrapper.eq("room_name", rooms.getRoomName());
//        if (roomsService.getOne(QueryWrapper) != null){
//            return AjaxResult.error("名称重复");
//        }
//        if (rooms.getBuildingId() == null){
//            return AjaxResult.error("请选择所属楼栋");
//        }
        AjaxResult validationResult = validateRooms(rooms);
        if (validationResult != null) {
            return validationResult;
        }
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
        Buildings building = buildingsService.getById(rooms.getBuildingId());
        rooms.setBuildingName(building.getBuildingName());
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

    private AjaxResult validateRooms(Rooms rooms){
        if (rooms.getBuildingId() == null) {
            return AjaxResult.error("请选择所属楼栋");
        }

        QueryWrapper<Rooms> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_name", rooms.getRoomName());
        if (rooms.getId() != null) {
            queryWrapper.ne("id", rooms.getId());
        }

        if (roomsService.getOne(queryWrapper) != null) {
            return AjaxResult.error("名称重复");
        }

        return null;
    }
}

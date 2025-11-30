package com.zxh.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Buildings;
import com.zxh.entity.Rooms;
import com.zxh.entity.Seats;
import com.zxh.service.impl.BuildingsServiceImpl;
import com.zxh.service.impl.RoomsServiceImpl;
import com.zxh.service.impl.SeatsServiceImpl;
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
 * 座位 前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@RestController
@RequestMapping("/sys/seats")
@Api(tags = "座位管理")
public class SeatsController {
    @Autowired
    private SeatsServiceImpl seatsService;

    @Autowired
    private RoomsServiceImpl roomsService;

    @Autowired
    private BuildingsServiceImpl buildingsService;

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

        Set<Integer> roomsIds = pages.getRecords().stream()
                .map(Seats::getRoomId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (!roomsIds.isEmpty()) {
            Map<Integer, Rooms> roomsMap = roomsService.listByIds(roomsIds)
                    .stream()
                    .collect(Collectors.toMap(Rooms::getId, Function.identity()));

            pages.getRecords().forEach(e -> {
                Rooms rooms = roomsMap.get(e.getRoomId());
                if (rooms != null) {
                    e.setRoomName(rooms.getRoomName());
                    e.setBuildingId(rooms.getBuildingId());
                }
            });
        }

        Set<Integer> buiIds = pages.getRecords().stream()
                .map(Seats::getBuildingId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (!buiIds.isEmpty()) {
            Map<Integer, Buildings> buiMap = buildingsService.listByIds(buiIds)
                    .stream()
                    .collect(Collectors.toMap(Buildings::getId, Function.identity()));

            pages.getRecords().forEach(e -> {
                Buildings buildings = buiMap.get(e.getBuildingId());
                if (buildings != null) {
                    e.setBuildingName(buildings.getBuildingName());
                }
            });
        }

        return AjaxResult.success(pages);
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Seats seats){
        AjaxResult validationResult = validateSeat(seats);
        if (validationResult != null) {
            return validationResult;
        }
        if(seatsService.save(seats)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("添加失败！");
        }
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Seats seats){
        AjaxResult validationResult = validateSeat(seats);
        if (validationResult != null) {
            return validationResult;
        }
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
        Rooms rooms = roomsService.getById(seats.getRoomId());
        seats.setRoomName(rooms.getRoomName());
        seats.setBuildingId(rooms.getBuildingId());
        Buildings buildings = buildingsService.getById(seats.getBuildingId());
        seats.setBuildingName(buildings.getBuildingName());
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

    private AjaxResult validateSeat(Seats seats) {
        if (seats.getRoomId() == null) {
            return AjaxResult.error("请选择所属自习室");
        }

        QueryWrapper<Seats> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seat_number", seats.getSeatNumber());
        if (seats.getId() != null) {
            queryWrapper.ne("id", seats.getId());
        }

        if (seatsService.getOne(queryWrapper) != null) {
            return AjaxResult.error("座位号重复");
        }

        return null;
    }
}

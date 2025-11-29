package com.zxh.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Buildings;
import com.zxh.service.impl.BuildingsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 栋楼 前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-29
 */
@RestController
@Api(tags = "栋楼管理")
@RequestMapping("/sys/buildings")
public class BuildingsController {

    @Autowired
    private BuildingsServiceImpl buildingsService;

    @ApiOperation("查列表")
    @GetMapping("/list")
    public AjaxResult list(Buildings buildings, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Buildings> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Buildings> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (buildings != null) {
            //查询条件
        }
        Page<Buildings> pages = buildingsService.page(page, queryWrapper);
        long count = buildingsService.count(queryWrapper);
        pages.setTotal(count);
        return AjaxResult.success(pages);
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Buildings buildings){
        QueryWrapper<Buildings> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("building_name", buildings.getBuildingName());
        if (buildingsService.getOne(QueryWrapper) != null){
            return AjaxResult.error("名称重复");
        }
        if(buildingsService.save(buildings)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("添加失败！");
        }
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Buildings buildings){
        if(buildingsService.updateById(buildings)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("修改失败!");
        }
    }

    @ApiOperation("单查")
    @GetMapping("/selectById/{id}")
    public AjaxResult selectById(@PathVariable Integer id){
        Buildings buildings = buildingsService.getById(id);
        return AjaxResult.success(buildings);
    }

    @ApiOperation("单删")
    @DeleteMapping("/deleteByid/{id}")
    public AjaxResult deleteByid(@PathVariable Integer id){
        if (buildingsService.removeById(id)) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error("删除失败！");
        }
    }

    @ApiOperation("多删")
    @DeleteMapping("/deleteByIds")
    public AjaxResult deleteByIds(@RequestBody List<Integer> ids){
        if (buildingsService.removeByIds(ids)) {
            return AjaxResult.success();
        }else{
            return AjaxResult.error("删除失败!");
        }
    }
}

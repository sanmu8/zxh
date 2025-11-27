package com.zxh.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Users;
import com.zxh.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-27
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersServiceImpl usersService;

    @GetMapping("/list")
    public AjaxResult list(Users users,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Users> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        if (users != null) {
           //查询条件
        }
        Page<Users> pages = usersService.page(page, queryWrapper);
        long count = usersService.count(queryWrapper);
        pages.setTotal(count);
        return AjaxResult.success(pages);
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody Users users){
        if(usersService.save(users)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("添加失败！");
        }
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody Users users){
        if(usersService.updateById(users)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("修改失败!");
        }
    }

    @GetMapping("/selectById/{id}")
    public AjaxResult selectById(@PathVariable Integer id){
        Users users = usersService.getById(id);
        return AjaxResult.success(users);
    }

    @DeleteMapping("/deleteByid/{id}")
    public AjaxResult deleteByid(@PathVariable Integer id){
        if (usersService.removeById(id)) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error("删除失败！");
        }
    }

    @DeleteMapping("/deleteByIds")
    public AjaxResult deleteByIds(@RequestBody List<Integer> ids){
        if (usersService.removeByIds(ids)) {
            return AjaxResult.success();
        }else{
            return AjaxResult.error("删除失败!");
        }
    }

}

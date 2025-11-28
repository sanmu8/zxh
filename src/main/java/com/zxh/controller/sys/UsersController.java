package com.zxh.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxh.entity.AjaxResult;
import com.zxh.entity.Users;
import com.zxh.service.impl.UsersServiceImpl;
import com.zxh.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2025-11-27
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersServiceImpl usersService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody Users user){
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            return AjaxResult.error("用户名或密码为空！");
        }
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, user.getUsername())
                .eq(Users::getPassword, user.getPassword());
        Users eqUser = usersService.getOne(wrapper);
        if (eqUser == null){
            return AjaxResult.error("用户名或密码错误！");
        }

        if (!eqUser.getUserType().equals("1")){
            return AjaxResult.error("权限不足！");
        }

        if (!Objects.equals(eqUser.getStatus(), "0")){
            return AjaxResult.error("账户已停用，请联系管理员！");
        }
        eqUser.setToken(JwtUtil.createToken(eqUser));
        eqUser.setPassword(null);
        return AjaxResult.success(eqUser);
    }

    @ApiOperation("查列表")
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

    @ApiOperation("添加")
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Users users){
        QueryWrapper<Users> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", users.getUsername());
        if (usersService.getOne(userQueryWrapper) != null){
            return AjaxResult.error("用户名已注册！");
        }
        if(usersService.save(users)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("添加失败！");
        }
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Users users){
        if(usersService.updateById(users)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error("修改失败!");
        }
    }

    @ApiOperation("单查")
    @GetMapping("/selectById/{id}")
    public AjaxResult selectById(@PathVariable Integer id){
        Users users = usersService.getById(id);
        return AjaxResult.success(users);
    }

    @ApiOperation("单删")
    @DeleteMapping("/deleteByid/{id}")
    public AjaxResult deleteByid(@PathVariable Integer id){
        if (usersService.removeById(id)) {
            return AjaxResult.success();
        }else {
            return AjaxResult.error("删除失败！");
        }
    }

    @ApiOperation("多删")
    @DeleteMapping("/deleteByIds")
    public AjaxResult deleteByIds(@RequestBody List<Integer> ids){
        if (usersService.removeByIds(ids)) {
            return AjaxResult.success();
        }else{
            return AjaxResult.error("删除失败!");
        }
    }

}

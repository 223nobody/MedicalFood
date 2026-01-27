package com.websocket.health.controller;

import com.websocket.health.pojo.Result;
import com.websocket.health.pojo.User;
import com.websocket.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/user")
    public Result queryUser(String account, String password)
    {
        User user = userService.queryUser(account, password);
        if(user!=null) return Result.success(user);
        else return Result.error("用户不存在");
    }
    @PostMapping("/user")
    public Result insertUser(@RequestBody User user)
    {
        User user1 = userService.queryUser(user.getAccount(), null);
        if (user1==null) return Result.success(userService.insertUser(user));
        else return Result.error("账户已经存在");
    }
    @PutMapping("/user")
    public Result updateUser(@RequestBody User user)
    {
        return Result.success(userService.updateUser(user));
    }
    @DeleteMapping("/user")
    public Result deleteUser(@RequestBody User user)
   {
        return Result.success(userService.deleteUser(user));
   }
}

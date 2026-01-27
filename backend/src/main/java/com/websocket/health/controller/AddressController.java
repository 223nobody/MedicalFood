package com.websocket.health.controller;

import com.websocket.health.pojo.Address;
import com.websocket.health.pojo.Result;
import com.websocket.health.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class AddressController {
    @Autowired
    AddressService addressService;
    @GetMapping("/address")
    public Result findAll(Integer userId)
    {
        List<Address> all = addressService.findAll(userId);
        Result success = Result.success(all);
        success.setTotal(all.size());
        return success;
    }
    @PostMapping("/address")
    public Result insertAddress(@RequestBody Address address)
    {
        return Result.success(addressService.insertAddress(address));
    }
    @PutMapping("/address")
    public Result updateAddress(@RequestBody Address address)
    {
        return Result.success(addressService.updateAddress(address));
    }
    @DeleteMapping("/address")
    public Result deleteAddress(Integer id)
    {
       return Result.success(addressService.deleteAddress(id));
    }
}

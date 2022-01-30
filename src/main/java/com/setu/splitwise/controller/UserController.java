package com.setu.splitwise.controller;


import com.setu.splitwise.dto.UserDto;
import com.setu.splitwise.model.User;
import com.setu.splitwise.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author amankumar
 */
@Api(value = "User Controller")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Find User", notes = "Find User")
    @GetMapping("/{id}")
    public ResponseEntity findUser(@PathVariable(value = "id") Long id) {
        User user = userService.getUser(id);
        if(ObjectUtils.isEmpty(user)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Create User", notes = "Create User")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = User.builder().name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber()).email(userDto.getEmail()).build();
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Update User", notes = "Update User")
    @PostMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "userId") Long userId,
                                           @RequestBody UserDto userDto) {
        User user = User.builder().name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber()).email(userDto.getEmail()).build();
        return new ResponseEntity<>(userService.updateUser(user, userId), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Delete User", notes = "Delete User")
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable(value = "userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

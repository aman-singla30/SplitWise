package com.setu.splitwise.controller;


import com.setu.splitwise.dto.AddMemberRequestDto;
import com.setu.splitwise.dto.GroupDto;
import com.setu.splitwise.model.Group;
import com.setu.splitwise.service.IGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author amankumar
 */
@Api(value = "Group Controller")
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Create Group", notes = "Create Group")
    @PostMapping("/create/{createdBy}")
    public ResponseEntity<Group> createGroup(@RequestBody GroupDto groupDto, @PathVariable("createdBy") Long createdBy) {
        Group group = Group.builder().name(groupDto.getName()).type(groupDto.getType()).build();
        return new ResponseEntity<>(groupService.createGroup(group, createdBy), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiOperation(value = "Add User into Group", notes = "Add User into Group")
    @PostMapping("/addMember/{groupId}")
    public ResponseEntity<Group> addMemberToGroup(@RequestBody AddMemberRequestDto addMemberRequestDto, @PathVariable("groupId") Long groupId) {
        return new ResponseEntity<>(groupService.addMemberToGroup(groupId, addMemberRequestDto), HttpStatus.OK);
    }

}

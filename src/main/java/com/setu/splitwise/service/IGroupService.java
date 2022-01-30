package com.setu.splitwise.service;


import com.setu.splitwise.dto.AddMemberRequestDto;
import com.setu.splitwise.model.Group;

/**
 * @author amankumar
 * Group Operations
 */
public interface IGroupService {

    /**
     * Create Group with one user who is creating this group
     * @param group
     * @param userId
     * @return Created group
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     */
    Group createGroup(Group group, Long userId);

    /**
     * Add Multiple users in existing group
     * @param groupId
     * @param addMemberRequestDto
     * @return updated group
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     * @throws com.setu.splitwise.exceptions.GroupNotFoundException
     */
    Group addMemberToGroup(Long groupId, AddMemberRequestDto addMemberRequestDto);

    /**
     * Fetch Existing group
     * @param groupId
     * @return Group
     * @throws com.setu.splitwise.exceptions.GroupNotFoundException
     */
    Group getGroup(Long groupId);
}

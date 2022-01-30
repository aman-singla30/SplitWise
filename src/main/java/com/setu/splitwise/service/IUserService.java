package com.setu.splitwise.service;

import com.setu.splitwise.model.User;

/**
 * @author amankumar
 * User Operations
 */
public interface IUserService {

    /**
     * Create User
     * @param user
     * @return Created User
     */
    User createUser(User user);

    /**
     * Update Existing user
     * @param user
     * @param userId
     * @return updated user
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     */
    User updateUser(User user, Long userId);

    /**
     * Delete User
     * @param userId
     * @return deleted userId
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     */
    Long deleteUser(Long userId);

    /**
     * Get Existing user
     * @param userId
     * @return user
     * @throws com.setu.splitwise.exceptions.UserNotFoundException
     */
    User getUser(Long userId);
}

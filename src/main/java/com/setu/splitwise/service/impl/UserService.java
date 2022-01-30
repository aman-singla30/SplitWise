package com.setu.splitwise.service.impl;


import com.setu.splitwise.dto.ErrorResponseDto;
import com.setu.splitwise.enums.ExceptionCode;
import com.setu.splitwise.exceptions.UserNotFoundException;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.UserRepository;
import com.setu.splitwise.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setCreationDate(LocalDate.now());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user, Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorResponseDto.builder()
                    .errorCode(ExceptionCode.U01)
                    .errorMessage(ExceptionCode.U01.getDescription())
                    .status(HttpStatus.NOT_FOUND).build());
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.setEmail(user.getEmail());
            dbUser.setName(user.getName());
            dbUser.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(dbUser);
        }
        throw new UserNotFoundException(ErrorResponseDto.builder()
                .errorCode(ExceptionCode.U02)
                .errorMessage(ExceptionCode.U02.getDescription())
                .status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    @Transactional
    public Long deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorResponseDto.builder()
                    .errorCode(ExceptionCode.U01)
                    .errorMessage(ExceptionCode.U01.getDescription())
                    .status(HttpStatus.NOT_FOUND).build());
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            userRepository.delete(dbUser);
            return dbUser.getId();
        }

        throw new UserNotFoundException(ErrorResponseDto.builder()
                .errorCode(ExceptionCode.U02)
                .errorMessage(ExceptionCode.U02.getDescription())
                .status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException(ErrorResponseDto.builder()
                .errorCode(ExceptionCode.U01)
                .errorMessage(ExceptionCode.U01.getDescription())
                .status(HttpStatus.NOT_FOUND).build());
    }
}

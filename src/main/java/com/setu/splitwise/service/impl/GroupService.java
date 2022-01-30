package com.setu.splitwise.service.impl;

import com.setu.splitwise.dto.AddMemberRequestDto;
import com.setu.splitwise.dto.ErrorResponseDto;
import com.setu.splitwise.enums.ExceptionCode;
import com.setu.splitwise.exceptions.UserNotFoundException;
import com.setu.splitwise.model.Group;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.GroupsRepository;
import com.setu.splitwise.repository.UserRepository;
import com.setu.splitwise.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author amankumar
 * To server group operation
 */
@Service
public class GroupService implements IGroupService {

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Group createGroup(Group group, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorResponseDto.builder()
                    .errorCode(ExceptionCode.G01)
                    .errorMessage(ExceptionCode.G01.getDescription())
                    .status(HttpStatus.NOT_FOUND).build());
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        User dbUser = optionalUser.get();
        group.setCreationDate(LocalDate.now());
        Set<User> users = new HashSet<>();
        users.add(dbUser);
        group.setUsers(users);

        return groupsRepository.save(group);
    }

    @Override
    @Transactional
    public Group addMemberToGroup(Long groupId, AddMemberRequestDto addMemberRequestDto) {
        if (!groupsRepository.existsById(groupId)) {
            throw new UserNotFoundException(ErrorResponseDto.builder()
                    .errorCode(ExceptionCode.G01)
                    .errorMessage(ExceptionCode.G01.getDescription())
                    .status(HttpStatus.NOT_FOUND).build());
        }
        for (Long userId : addMemberRequestDto.getUserIds()) {
            if (!userRepository.existsById(userId)) {
                throw new UserNotFoundException(ErrorResponseDto.builder()
                        .errorCode(ExceptionCode.U01)
                        .errorMessage(ExceptionCode.U01.getDescription())
                        .status(HttpStatus.NOT_FOUND).build());
            }
        }
        List<User> allUsers = userRepository.findAllById(addMemberRequestDto.getUserIds());
        Set<User> users = new HashSet<>(allUsers);
        Optional<Group> optionalGroup = groupsRepository.findById(groupId);
        Group group = optionalGroup.get();
        group.getUsers().addAll(users);
        return groupsRepository.save(group);
    }

    @Override
    public Group getGroup(Long groupId) {
        Optional<Group> optionalGroup = groupsRepository.findById(groupId);

        if(optionalGroup.isPresent()) {
            return optionalGroup.get();
        }
        throw new UserNotFoundException(ErrorResponseDto.builder()
                .errorCode(ExceptionCode.G01)
                .errorMessage(ExceptionCode.G01.getDescription())
                .status(HttpStatus.NOT_FOUND).build());
    }
}

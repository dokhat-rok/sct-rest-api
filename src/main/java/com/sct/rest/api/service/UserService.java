package com.sct.rest.api.service;

import com.sct.rest.api.mapper.user.UserMapper;
import com.sct.rest.api.model.dto.UserDto;
import com.sct.rest.api.model.entity.User;
import com.sct.rest.api.repository.UserRepository;
import com.sct.rest.api.security.CallContext;
import com.sct.rest.api.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserById(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), id));
        return userMapper.modelToDto(user);
    }

    public UserDto getCurrent(){
        CallContext context = SecurityContext.get();
        UserDto userDto = new UserDto();
        userDto.setId(context.getUserId());
        userDto.setLogin(context.getUserLogin());
        userDto.setRole(context.getUserRole());
        userDto.setBalance(context.getUserBalance());
        return userDto;
    }

    public UserDto getUserByLogin(String login){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByLogin(login));
        User user = userOptional.orElseThrow(() -> new ServiceRuntimeException(ErrorCodeEnum.USER_NOT_FOUND, new Throwable(), login));
        return userMapper.modelToDto(user);
    }

    public boolean userExistByLogin(String login){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByLogin(login));
        return userOptional.isPresent();
    }

    public void createUser(User user){
        userRepository.save(user);
    }
}
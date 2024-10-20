package com.mantisa.inventory.service;


import com.mantisa.inventory.model.UserEntity;
import com.mantisa.inventory.model.dto.AuthCreateUserRequestDTO;

import java.util.List;

public interface IUserService {
    UserEntity createUser(AuthCreateUserRequestDTO userEntity);

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long userId);

    boolean deleteUserById(Long userId);

    UserEntity findByUsername(String username);

}

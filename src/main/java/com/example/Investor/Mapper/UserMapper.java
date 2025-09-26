package com.example.Investor.Mapper;

import com.example.Investor.DTO.RegisterRequest;
import com.example.Investor.Entity.Role;
import com.example.Investor.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterRequest toRegisterRequest(UserEntity userEntity);
    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(RegisterRequest registerRequest);
}

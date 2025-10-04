package com.example.Investor.Mapper;

import com.example.Investor.DTO.RegisterRequest;
import com.example.Investor.DTO.UserDTO;
import com.example.Investor.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterRequest toRegisterRequest(UserEntity userEntity);
    @Mapping(target = "roles", ignore = true)
    UserEntity toUserEntity(RegisterRequest registerRequest);

    UserDTO toUserDTO(UserEntity userEntity);
}

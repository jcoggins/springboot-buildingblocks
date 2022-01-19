package com.stacksimplify.restservices.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.stacksimplify.restservices.dtos.UserMsDto;
import com.stacksimplify.restservices.entities.User;

@Mapper(componentModel = "Spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	// User To User DTO
	@Mappings({
			@Mapping(source = "role", target = "rolename"),
			@Mapping(source = "email", target = "emailaddress")
	})
	UserMsDto userToUserDto(User user);
	
	// List<User> to List<UserMsDto>
	List<UserMsDto> usersToUserDtos(List<User> users);
	
}

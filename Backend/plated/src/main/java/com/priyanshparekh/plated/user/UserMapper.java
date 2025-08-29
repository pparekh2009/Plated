package com.priyanshparekh.plated.user;

import com.priyanshparekh.plated.user.dto.UserSignUpRequest;
import com.priyanshparekh.plated.user.dto.UserSignUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toUser(UserSignUpRequest userSignUpRequest);

//    @Mapping(target = "password", ignore = true)
    UserSignUpResponse toDto(User user);
}

package sk.stasko.mapper;

import sk.stasko.dto.UserDto;
import sk.stasko.model.UserEntity;

public class UserMapper {
    public UserEntity toEntity(UserDto dto) {
        return new UserEntity(dto.id(), dto.guid(), dto.name());
    }

    public UserDto toDto(UserEntity entity) {
        return new UserDto(entity.id(), entity.guid(), entity.name());
    }
}

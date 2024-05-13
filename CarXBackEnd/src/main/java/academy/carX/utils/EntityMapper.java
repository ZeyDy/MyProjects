package academy.carX.utils;

import java.util.Collection;

import academy.carX.dto.UserDto;
import academy.carX.models.UserEntity;
import academy.carX.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;



@Component
public class EntityMapper {

    public UserEntity toUserEntity(UserDto dto) {

        UserEntity entity = new UserEntity();
        entity.setId( dto.getId());
        entity.setUsername( dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public UserDto toUserDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRoles()
        );
    }

}
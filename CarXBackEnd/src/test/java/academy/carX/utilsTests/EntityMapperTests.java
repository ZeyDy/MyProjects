package academy.carX.utilsTests;

import academy.carX.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

import academy.carX.models.UserEntity;
import academy.carX.utils.EntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class EntityMapperTests {

    @Autowired
    EntityMapper entityMapper;

    @Test
    void toUserDto_ShouldCorrectlyMapUserEntityToUserDto(){
        //Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setId( 15L );
        userEntity.setUsername("someUserName");
        userEntity.setEmail("some@email.com");

        //Act
        UserDto result = entityMapper.toUserDto(userEntity);

        //Assert
        assertThat( result.getId()).isEqualTo(  userEntity.getId());
        assertThat( result.getUsername()).isEqualTo(  userEntity.getUsername());
        assertThat( result.getEmail()).isEqualTo(  userEntity.getEmail());

    }

    @Test
    void toUserEntity_ShouldCorrectlyMapUserDtoToUserEntity() {
        UserDto userDto = new UserDto( 20L, "testUser", "test@example.com");

        UserEntity result = entityMapper.toUserEntity(userDto);

        assertThat(result.getId()).isEqualTo(userDto.getId());
        assertThat(result.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(result.getEmail()).isEqualTo(userDto.getEmail());
    }

}
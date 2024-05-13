package academy.carX.servicesTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import academy.carX.dto.UserDto;
import academy.carX.models.UserEntity;
import academy.carX.repositories.UserRepository;
import academy.carX.services.UserService;
import academy.carX.utils.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper entityMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, entityMapper);
    }

    @Test
    void createUser_ShouldReturnUserDto() {
        //Arrange
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(entityMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(entityMapper.toUserDto(any(UserEntity.class))).thenReturn(userDto);

        //Act
        UserDto createdUser = userService.createUser(new UserDto());

        //Assert
        assertThat(createdUser).isNotNull();
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserDto() {

        //Arrange
        List<UserEntity> userEntities = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(userEntities);


        //Act
        List<UserDto> users = userService.getAllUsers();


        //Assert
        assertThat(users).isNotNull();

    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUserDto() {
        //Arrange
        Optional<UserEntity> userEntity = Optional.of(new UserEntity());

        when(userRepository.findById(any(Long.class))).thenReturn(userEntity);
        when(entityMapper.toUserDto(any(UserEntity.class))).thenReturn(new UserDto());


        //Act
        Optional<UserDto> userDto = userService.getUserById(1L);


        //Assert
        assertThat(userDto).isPresent();
    }

    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUserDto() {

        //Arrange
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(entityMapper.toUserEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(entityMapper.toUserDto(any(UserEntity.class))).thenReturn(userDto);


        //Act
        Optional<UserDto> updatedUser = userService.updateUser(1L, new UserDto());

        //Assert
        assertThat(updatedUser).isPresent();
    }

    @Test
    void deleteUser_ShouldInvokeDeleteMethod() {
        //Arrange
        Long userId = 1L;

        //Act
        userService.deleteUser(userId);


        //Assert
        Mockito.verify(userRepository).deleteById(userId);
    }


}

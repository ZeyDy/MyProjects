package academy.carX.controllers;

import java.util.List;
import java.util.Optional;

import academy.carX.dto.UserDto;
import academy.carX.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    /**
     * Constructs a UserController with the specified UserService.
     * @param userService The service to manage users.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Creates a new user with the provided user details.
     * @param userDto The UserDto containing the user's data.
     * @return ResponseEntity containing the created UserDto and HTTP status CREATED.
     */
    @PostMapping("/")
    public ResponseEntity<UserDto>  createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    /**
     * Retrieves all users.
     * @return ResponseEntity containing a list of UserDto and HTTP status OK.
     */
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    /**
     * Retrieves a user by their ID.
     * @param id The unique identifier of the user.
     * @return ResponseEntity containing the UserDto if found, otherwise not found status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        Optional<UserDto> userInBox = userService.getUserById(id);
        return userInBox
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build());
    }
    /**
     * Updates a user by their ID with provided user data.
     * @param id The unique identifier of the user to update.
     * @param userDto The UserDto containing the updated data.
     * @return ResponseEntity containing the updated UserDto if successful, otherwise not found status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto){
        Optional<UserDto> userInBox = userService.updateUser(id, userDto);

        return userInBox
                .map( ResponseEntity::ok )
                .orElseGet( () -> ResponseEntity.notFound().build());
    }
    /**
     * Deletes a user by their ID.
     * @param id The unique identifier of the user to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT to indicate successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
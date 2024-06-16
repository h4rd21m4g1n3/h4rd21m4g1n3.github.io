package cz.cvut.fel.nss.sem.controller;

import cz.cvut.fel.nss.sem.exceptions.NotFoundException;
import cz.cvut.fel.nss.sem.model.User;
import cz.cvut.fel.nss.sem.model.UserDTO;
import cz.cvut.fel.nss.sem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/***
 * User controller
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /***
     * Method for getting all users through http GET method
     * @return List of all users
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()){
            throw new NotFoundException("No users were found");
        }

        return users;
    }

    /***
     * Method for getting one specific user
     * @param username - users name
     * @return User
     */
    @GetMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUser(@PathVariable String username){
        final User user = userService.findByUsername(username);
        if (user == null){
            throw NotFoundException.create("User", username);
        }

        return new UserDTO(user.getEmail(), user.getRole().toString());
    }

    /***
     * Method for register some user
     * @param user User object
     * @return Response entity
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        userService.registerUser(user);

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{username}", user.getUsername());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /***
     * Method for updating user info
     * @param username - users name
     * @param updatedUser User i want to update
     */
    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        User user = userService.findByUsername(username);
        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        userService.updateUser(user);
    }


}


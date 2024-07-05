package cz.cvut.fel.nss.sem.service;

import com.sun.tools.attach.AgentInitializationException;
import cz.cvut.fel.nss.sem.exceptions.InvalidUsernameException;
import cz.cvut.fel.nss.sem.model.User;
import cz.cvut.fel.nss.sem.repository.UserRepository;
import cz.cvut.fel.nss.sem.security.Optional;
import cz.cvut.fel.nss.sem.utils.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepoMock;

    private UserService userSerMock;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void registerUserRegistersCorrectly(){
        userSerMock = new UserService(userRepoMock, encoder);

        User user = Generator.generateUser(2,2);

        userSerMock.registerUser(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepoMock).save(captor.capture());
    }

    @Test
    public void registerWithDuplicateUsernameThrowsInvalid(){
        userService = new UserService(userRepo, encoder);

        User user = Generator.generateUser(2,2);

        user.setUsername("test");

        User duplicate = Generator.generateUser(1,1);

        duplicate.setUsername("test");

        userService.registerUser(user);

        assertThrows(InvalidUsernameException.class, () -> {
            userService.registerUser(duplicate);
        });
    }

    @Test
    public void findByUsernameFindCorrect(){
        userService = new UserService(userRepo, encoder);

        User user = Generator.generateUser(1,1);

        userService.registerUser(user);

        User foundUser = userService.findByUsername(user.getUsername());

        assertEquals(user.getEmail(), foundUser.getEmail());

        assertEquals(user.getFirstName(), foundUser.getFirstName());

        assertEquals(user.getLastName(), foundUser.getLastName());

        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void findByIdFindCorrect(){
        userService = new UserService(userRepo, encoder);

        User user = Generator.generateUser(1,1);

        userService.registerUser(user);

        User foundUser = userService.findById(user.getId());

        assertEquals(user.getUsername(), foundUser.getUsername());

        assertEquals(user.getEmail(), foundUser.getEmail());

        assertEquals(user.getFirstName(), foundUser.getFirstName());

        assertEquals(user.getLastName(), foundUser.getLastName());

        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void findAllFindsAll(){
        userService = new UserService(userRepo, encoder);

        List<User> users = new ArrayList<>();

        for (int i = 1; i < 5 ; i++) {
            User user = Generator.generateUser(i,i);
            user.setId((long) i);
            users.add(user);
        }

        for (User us: users) {
            userService.registerUser(us);
        }

        List<User> foundUsers = userService.findAll();

        assertEquals(users.size(), foundUsers.size());
    }

    @Test
    public void checkLoginReturnsTrueForCorrectCredentials(){
        userService = new UserService(userRepo,encoder);

        User user = Generator.generateUser(2,2);
        user.setUsername("user");
        user.setPassword("pass");

        Optional<User> future = new Optional<>();

        userService.registerUser(user);

        assertTrue(userService.checkLogin("user","pass", future));
    }

    @Test
    public void checkLoginReturnsFalseForIncorrectCredentials(){
        userService = new UserService(userRepo,encoder);

        User user = Generator.generateUser(2,2);
        user.setUsername("user");
        user.setPassword("pass");

        Optional<User> future = new Optional<>();

        userService.registerUser(user);

        assertFalse(userService.checkLogin("user","test", future));
    }

    @Test
    public void passingNullParametersThrowsNullPointerException(){
        userService = new UserService(userRepo,encoder);

        assertThrows(NullPointerException.class, () ->{
            userService.findById(null);
        });

        assertThrows(NullPointerException.class, () ->{
            userService.registerUser(null);
        });

        assertThrows(NullPointerException.class, () ->{
            userService.findByUsername(null);
        });

        assertThrows(NullPointerException.class, () ->{
            userService.checkLogin(null,null,null);
        });

        assertThrows(NullPointerException.class, () ->{
            userService.updateUser(null);
        });
    }




}

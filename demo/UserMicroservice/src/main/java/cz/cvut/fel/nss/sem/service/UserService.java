package cz.cvut.fel.nss.sem.service;

import cz.cvut.fel.nss.sem.exceptions.InvalidUsernameException;
import cz.cvut.fel.nss.sem.exceptions.NotFoundException;
import cz.cvut.fel.nss.sem.model.Role;
import cz.cvut.fel.nss.sem.model.User;
import cz.cvut.fel.nss.sem.repository.UserRepository;
import cz.cvut.fel.nss.sem.security.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
public class UserService {

    private final UserRepository repo;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public List<User> findAll(){
        return repo.findAll();
    }

    @Transactional
    public User findById(Long id){
        Objects.requireNonNull(id);
        java.util.Optional<User> opt = repo.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }

        throw NotFoundException.create("Username",id);
    }

    @Transactional
    public User findByUsername(String username){
        Objects.requireNonNull(username);
        return repo.findByUsername(username);
    }

    @Transactional
    public void registerUser(User user){
        Objects.requireNonNull(user);

        if(repo.existsByUsername(user.getUsername())){
            throw new InvalidUsernameException("Username is already taken.");
        }

        user.encodePassword(encoder);
        if (user.getRole() == null){
            user.setRole(Role.USER);
        }

        repo.save(user);
    }

    @Transactional
    public void updateUser(User user){
        Objects.requireNonNull(user);
        repo.save(user);
    }

    @Transactional
    public boolean checkLogin(String username, String password, Optional<User> future){
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(future);

        User user = repo.findByUsername(username);
        future.set(user);
        return user != null && encoder.matches(password, user.getPassword());
    }
}

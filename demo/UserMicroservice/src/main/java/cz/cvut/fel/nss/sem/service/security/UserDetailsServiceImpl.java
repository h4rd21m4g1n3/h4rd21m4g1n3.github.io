package cz.cvut.fel.nss.sem.service.security;

import cz.cvut.fel.nss.sem.model.User;
import cz.cvut.fel.nss.sem.repository.UserRepository;
import cz.cvut.fel.nss.sem.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new CustomUserDetails(user);
    }
}

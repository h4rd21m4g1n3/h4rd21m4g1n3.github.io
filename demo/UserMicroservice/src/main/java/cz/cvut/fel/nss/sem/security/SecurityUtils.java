package cz.cvut.fel.nss.sem.security;

import cz.cvut.fel.nss.sem.model.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

public class SecurityUtils {

    public static User getCurrentUser(){
        final SecurityContext context = SecurityContextHolder.getContext();
        assert context != null;
        final CustomUserDetails userDetails = (CustomUserDetails) context.getAuthentication().getDetails();
        return userDetails.getUser();
    }

    public static CustomUserDetails getCurrentUserDetails() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null && context.getAuthentication().getDetails() instanceof CustomUserDetails) {
            return (CustomUserDetails) context.getAuthentication().getDetails();
        } else {
            return null;
        }
    }

    public static AuthenticationToken setCurrentUser(CustomUserDetails userDetails) {
        final AuthenticationToken token = new AuthenticationToken(userDetails.getAuthorities(), userDetails);
        token.setAuthenticated(true);

        final SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
        return token;
    }

    public static boolean isAuthenticatedAnonymously() {
        return getCurrentUserDetails() == null;
    }

}

package com.ust.AuthenticationMicroservice.authenticationMicroservice.config;


import com.ust.AuthenticationMicroservice.authenticationMicroservice.model.User;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.repository.UserRepository;
import com.ust.AuthenticationMicroservice.authenticationMicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService usrService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usrService.saveEncryptedpw(username);
        Optional<User> userInfo = userRepository.findByName(username);
        userInfo = usrService.saveEncryptedpw(username);
        return userInfo.map(UserAuthDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}

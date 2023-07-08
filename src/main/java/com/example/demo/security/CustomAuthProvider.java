package com.example.demo.security;
import com.example.demo.data.PersonDataService;
import com.example.demo.data.UserDataService;
import com.example.demo.model.Person;

import com.example.demo.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
    @Autowired
    UserDataService dataService;
    @Autowired
    BCryptPasswordEncoder encoder;
    Logger logger  = LogManager.getLogger();
    @Override
    public UsernamePasswordAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Starting auth...");
        logger.info(authentication.getCredentials().toString());
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<User> user = dataService.findByName(username);
        if(!user.isPresent()){
            logger.error(authentication.toString());
            logger.error("User didn't found");
            return null;

        }
        else {
            if(!authWithPassword(password,user.get().getPassword())){
                logger.error("Bad password 1");
                logger.error(password+" != "+user.get().getPassword());
                throw new BadCredentialsException("Bad Password");
            }
            else {
                logger.info("Returning token");
                logger.info(new UsernamePasswordAuthenticationToken(username,password,authorities).toString());
                return new UsernamePasswordAuthenticationToken(username,password,authorities);
            }
        }

    }
    public boolean authWithPassword(String entered,String fromBase){
        return entered.equals(fromBase);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

package com.hmp.role.services;

import com.hmp.role.entity.User;
import com.hmp.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       List<User>  user = userRepository.findByUserName(userName);
        return user.stream().map(GroupUserDetails::new).findAny().orElseThrow(()-> new UsernameNotFoundException("User not found "+userName));

    }
}

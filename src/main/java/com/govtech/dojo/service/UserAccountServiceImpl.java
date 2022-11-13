package com.govtech.dojo.service;

import com.govtech.dojo.model.Role;
import com.govtech.dojo.model.UserAccount;
import com.govtech.dojo.repository.RoleRespository;
import com.govtech.dojo.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserAccountServiceImpl implements UserAccountService {

    @Resource
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RoleRespository roleRespository;

    public UserAccount findUserByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public void saveUser(UserAccount userAccount) {
        userAccount.setPassword(userAccount.getPassword());
        userAccount.setActive(1);
        Role userRole = roleRespository.findByRole("ADMIN");
        if (userRole == null) {
            userRole = new Role();
        }
        userAccount.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userAccountRepository.save(userAccount);
    }
}
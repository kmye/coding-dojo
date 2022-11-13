package com.govtech.dojo.service;

import com.govtech.dojo.model.UserAccount;

public interface UserAccountService {

    UserAccount findUserByEmail(String email);

    void saveUser(UserAccount userAccount);
}
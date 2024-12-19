package ru.otus.services;

import ru.otus.crm.service.DBServiceClient;

public class UserAuthServiceImpl implements UserAuthService {

    public UserAuthServiceImpl() {
    }

    public static final String ADMIN_LOGIN = "login";
    public static final String ADMIN_PASS = "password";

    @Override
    public boolean authenticate(String login, String password) {
        return login.equals(ADMIN_LOGIN) && password.equals(ADMIN_PASS);
    }
}

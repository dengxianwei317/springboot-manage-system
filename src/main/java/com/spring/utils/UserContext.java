package com.spring.utils;

import com.spring.entity.system.SystemUser;

/*
* 上下文用户对象
* */
public class UserContext implements AutoCloseable {

    static final ThreadLocal<SystemUser> current = new ThreadLocal<>();

    public UserContext(SystemUser user) {
        current.set(user);
    }

    public static SystemUser getCurrentUser() {
        return current.get();
    }

    public static void setCurrentUser(SystemUser user) {
        current.set(user);
    }

    @Override
    public void close() throws Exception {
        current.remove();
    }
}

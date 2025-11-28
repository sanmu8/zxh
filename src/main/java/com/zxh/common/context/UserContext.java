package com.zxh.common.context;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private static final ThreadLocal<Integer> userHolder = new ThreadLocal<>();

    public static void setCurrentUser(Integer user) {
        userHolder.set(user);
    }

    public static Integer getCurrentUser() {
        return userHolder.get();
    }

    public static void clear(){
        userHolder.remove();
    }
}

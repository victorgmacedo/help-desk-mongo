package br.com.helpdesk.api.config;

import org.springframework.security.core.userdetails.UserDetails;

public class Context {

    static ThreadLocal<UserDetails> threadLocal = new ThreadLocal();

    private Context(){

    }

    public static void setUser(UserDetails user){
        threadLocal.set(user);
    }

    public static UserDetails getUser(){
        return threadLocal.get();
    }

}

package br.com.helpdesk.api.security.jwt;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.enums.ProfileEnum;

public class JwtUserFactory {

    private JwtUserFactory(){

    }

    public static JwtUser create(User user){
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGrantAuthority(user.getProfile()));
    }

    private static List<GrantedAuthority> mapToGrantAuthority(ProfileEnum profileEnum){
        return  Arrays.asList((new SimpleGrantedAuthority(profileEnum.toString())));
    }

}

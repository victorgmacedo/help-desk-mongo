package br.com.helpdesk.api.security.jwt;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.enums.ProfileEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory(){

    }

    public static JwtUser create(User user){
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGrantAuthority(user.getProfile()));
    }

    private static List<GrantedAuthority> mapToGrantAuthority(ProfileEnum profileEnum){
        return List.of(new SimpleGrantedAuthority(profileEnum.toString()));
    }

}

package br.com.helpdesk.api.security.model;

import br.com.helpdesk.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentUser {

    private String token;
    private User user;

}

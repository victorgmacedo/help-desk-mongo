package br.com.helpdesk.api.entity;

import br.com.helpdesk.api.enums.ProfileEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document
@Data
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email required")
    @Email(message = "Email invalid")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 6)
    private String password;

    private ProfileEnum profile;
}

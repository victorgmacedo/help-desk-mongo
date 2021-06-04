package br.com.helpdesk.api.entity;

import br.com.helpdesk.api.enums.PriorityEnum;
import br.com.helpdesk.api.enums.StatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class Ticket {

    @Id
    private String id;

    @DBRef(lazy = true)
    private User user;

    private LocalDateTime date;

    private String title;

    private Long number;

    private StatusEnum status;

    private PriorityEnum priority;

    @DBRef(lazy = true)
    private User assignedUser;

    private String description;

    private String image;

    @Transient
    private List<ChangeStatus> changes;
}

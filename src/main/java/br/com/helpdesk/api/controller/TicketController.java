package br.com.helpdesk.api.controller;

import br.com.helpdesk.api.config.Context;
import br.com.helpdesk.api.entity.Ticket;
import br.com.helpdesk.api.enums.StatusEnum;
import br.com.helpdesk.api.response.Response;
import br.com.helpdesk.api.security.jwt.JwtTokenUtil;
import br.com.helpdesk.api.services.TicketService;
import br.com.helpdesk.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<Ticket>> create(HttpServletRequest request, @RequestBody Ticket ticket){
        Response<Ticket> response = new Response<>();
        ticket.setStatus(StatusEnum.NEW);
        ticket.setUser(userService.findByEmail(Context.getUser().getUsername()));
        ticket.setDate(LocalDateTime.now());
        ticket.setNumber(new Random(9999).nextLong());
        response.setData(ticketService.createOrUpdate(ticket));
        return ResponseEntity.ok(response);
    }

}

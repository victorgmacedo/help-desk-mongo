package br.com.helpdesk.api.controller;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.response.Response;
import br.com.helpdesk.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody @Valid User user, BindingResult result){
        Response<User> response = new Response<>();
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            response.setData(userService.createOrUpdate(user));
        }catch (DuplicateKeyException dE){
            response.getErrors().add("E-mail already registered !!");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<User>> findById(@PathVariable("id") String id){
        Response<User> response = new Response<User>();
        response.setData(userService.findById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<User>>> findAll(Pageable page){
        Response<Page<User>> response = new Response<>();
        response.setData(userService.findAll(page));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<User>> delete(@PathVariable("id") String id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }


}

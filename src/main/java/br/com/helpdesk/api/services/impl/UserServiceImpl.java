package br.com.helpdesk.api.services.impl;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.exception.NotFoundException;
import br.com.helpdesk.api.repository.UserRepository;
import br.com.helpdesk.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void delete(String id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable page) {
        return this.userRepository.findAll(page);
    }
}

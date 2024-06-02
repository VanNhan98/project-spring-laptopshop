package vn.laptop.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "hello from service";
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }
}

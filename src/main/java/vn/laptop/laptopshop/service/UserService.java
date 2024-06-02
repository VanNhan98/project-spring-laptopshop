package vn.laptop.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // public String handleHello() {
    // return "hello from service";
    // }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User handleSaveUser(User user) {
        User nhan = this.userRepository.save(user);
        System.out.println(nhan);
        return nhan;

    }
}

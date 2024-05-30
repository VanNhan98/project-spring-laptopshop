package vn.laptop.laptopshop.controller;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.repository.UserRepository;
import vn.laptop.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("user", test);
        return "hello";
    }

    @RequestMapping(value = "/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // truyền data từ view đến controller
    // @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    // public String createUserPage(Model model, @ModelAttribute("newUser") User
    // nhan) {
    // System.out.println("run time" + nhan);
    // this.userRepository.save(nhan);
    // return "hello";
    // }

    @RequestMapping(value = "/admin/user/create")
    public String createUserPage(Model model) {
        System.out.println("run time");
        // this.userRepository.save(nhan);
        return "hello";
    }

}

// @RestController
// public class UserController {

// // DI : dependency injection
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("")
// public String getHomePage() {
// return this.userService.handleHello();
// }
// }

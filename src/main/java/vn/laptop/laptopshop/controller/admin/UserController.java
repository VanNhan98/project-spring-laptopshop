package vn.laptop.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("maivanhan@gmail.com");
        System.out.println(arrUsers);
        return "hello";
    }

    // lay thong tin ben form va hien thi vs id, email va fullname
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    // chuyen qua url va hien thong tin chi tiet user
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        // model.addAttribute("id", id);
        return "admin/user/detail";
    }

    // hien thi giao dien form
    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // update
    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User nhan) {
        User currentUser = this.userService.getUserById(nhan.getId());
        if (currentUser != null) {
            currentUser.setAddress(nhan.getAddress());
            currentUser.setEmail(nhan.getEmail());
            currentUser.setFullName(nhan.getFullName());
            currentUser.setPhoneNumber(nhan.getPhoneNumber());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    // delete
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        // User currentUser = this.userService.getUserById(id);
        // model.addAttribute("newUser", currentUser);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUserPage(Model model, @ModelAttribute("newUser") User nhan) {
        this.userService.deleteUser(nhan.getId());
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User nhan) {
        this.userService.handleSaveUser(nhan);
        return "redirect:/admin/user";
    }
}
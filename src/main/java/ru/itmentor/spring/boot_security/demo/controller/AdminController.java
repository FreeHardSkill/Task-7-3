package ru.itmentor.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String mainPage(Model model) {
        model.addAttribute("users",userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id")long id, Model model) {
        model.addAttribute("user",userService.findById(id));
        return "admin/id";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping("")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (userService.findByEmail(user.getEmail()) != null) bindingResult.rejectValue("email", "error.user", "Email уже используется!");
        if(bindingResult.hasErrors()) return "admin/new";
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String updatingUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user",userService.findById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @RequestMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
        // ничего не делаем
    }

}

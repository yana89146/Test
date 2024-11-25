package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;


@Controller
public class TestController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @PostMapping(value = "/admin/newUser")
    @ResponseBody
    public User create() {
        User user = new User();
        return user;
    }

    @ResponseBody
    @PutMapping(value = "/admin/user", produces = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateById(user, user.getId());
        return new ResponseEntity(user, HttpStatus.CREATED);
    }


    @ResponseBody
    @GetMapping(value = "/admin/user",consumes = "application/json", produces = "application/json")
    public User user(@RequestBody User user) {
        return user;
    }


    @DeleteMapping(value = "/admin/user")
    @ResponseBody
    public User delete() {
        User user = new User();
        user.setId(18L);
        user.setUsername("deletedUser");
        return user;
    }

//___________________________________________________________________


    @GetMapping(value = "/admin/showList")
    public String showList(ModelMap model, Authentication authentication) {
        User authenUser = userService.findByUsername(authentication.getName());
        model.addAttribute("authUser", authenUser);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "result";
    }

    @GetMapping(value = "/admin/test")
    public String test() {
        return "test";
    }


    @GetMapping(value = "/admin/newUser")
    public String newUser(ModelMap model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("authenUser", user);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping(value = "/admin/showList")
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/showList";
    }


}



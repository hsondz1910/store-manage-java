package com.lastterm.java.Controllers;

import com.lastterm.java.Models.User;
import com.lastterm.java.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private UserRepository db;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        User foundUser = db.findByEmail(user.getEmail());

        if (foundUser != null && user.getPassword().equals(foundUser.getPassword())) {
            // Đăng nhập thành công, chuyển hướng đến productManagement
            return "redirect:/products";
        } else {
            // Đăng nhập thất bại, hiển thị lỗi
            model.addAttribute("loginError", "Thông tin đăng nhập không chính xác");
            return "login";
        }
    }
}

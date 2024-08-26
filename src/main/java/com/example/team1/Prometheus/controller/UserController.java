package com.example.team1.Prometheus.controller;

import com.example.team1.Prometheus.entity.User;
import com.example.team1.Prometheus.entity.UserDto;
import com.example.team1.Prometheus.service.UserFilter;
import com.example.team1.Prometheus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final UserFilter userFilter;

    private User userForRedirect;

    // 첫 홈 화면
    @GetMapping("/home")
    public String welcomeHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "redirect:/items";
        } else {
            return "/home";
        }
    }

    @GetMapping("/users/join")
    public String joinForm(Model model) {
        return "users/join";
    }

    @PostMapping("/users/join")
    public String CreateUser(UserDto form, @RequestParam("password_check") String password_check, HttpServletRequest httpServletRequest) {
        return userService.createUser(form, password_check, httpServletRequest);
    }

    @GetMapping("/users/login")
    public String login() {
        return "users/login";
    }

    @PostMapping("/users/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest httpServletRequest) {
        return userService.login(username, password, httpServletRequest);
    }

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        return userService.logout(httpServletRequest);
    }

    // 회원 상세 페이지
    @GetMapping("/users/{userid}")
    public String profile(@PathVariable Long userid, Model model) {
        userForRedirect = userService.findUserById(userid);
        return "redirect:/users/profile";

    }

    @GetMapping("/users/profile")
    public String profile(Model model) {
        User user = userForRedirect;
        model.addAttribute("userid", user.getUserId());
        model.addAttribute("username", user.getUserName());

        userService.getItemsByUserId(user.getUserId(), model);
        userForRedirect = null;
        return "users/profile";
    }

    // 마이페이지
    @GetMapping("/users/mypage")
    public String mypage(Model model) {
        User user = userFilter.findUserByFilter(model);
        userService.getItemsByUserId(user.getUserId(), model);

        return "users/mypage";

    }


}

package com.my.mapService.controller;

import com.my.mapService.dto.User;
import com.my.mapService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 페이지
    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("signUp", new User());
        return "users/signUp";
    }

    // 회원가입 처리
    @PostMapping("/signUp")
    public String signUpProcess(@ModelAttribute User user) {
        userService.signIn(user);  // 서비스에서 회원가입 처리
        return "redirect:/logIn";  // 로그인 페이지로 리다이렉트
    }

    // 로그인 페이지
    @GetMapping("/logIn")
    public String logIn() {
        return "users/logIn";
    }

    // 로그인 처리
    @PostMapping("/logIn")
    public String logInProcess(@RequestParam("userId") String userId, @RequestParam("password") String password, Model model) {
        // 로그인 검증
        User user = userService.findById(userId).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            // 로그인 성공 시 회원 목록을 모델에 추가하고 리스트 페이지로 이동
            model.addAttribute("users", userService.findAll());
            return "redirect:/list";  // 로그인 후 회원 리스트 페이지로 이동
        } else {
            // 로그인 실패 시 로그인 페이지로 돌아가기
            model.addAttribute("error", "Invalid username or password");
            return "users/logIn";
        }
    }

    // 회원 리스트 페이지
    @GetMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    // 사용자 수정 페이지 (수정 폼)
    @GetMapping("/update/{userId}")
    public String updateUserForm(@PathVariable("userId") String userId, Model model) {
        // 사용자 정보를 찾아서 수정 폼에 전달
        User user = userService.findById(userId).orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
            return "users/update";  // 수정 폼 페이지로 이동
        }
        return "redirect:/list";  // 사용자가 없으면 리스트로 돌아가기
    }

    // 사용자 수정 처리
    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId, @ModelAttribute User updatedUser) {
        // updatedUser에서 userId는 수정하지 않음
        updatedUser.setUserId(userId);  // userId는 수정하지 않음 (컨트롤러에서 그대로 설정)
        userService.update(updatedUser);  // 사용자 정보 업데이트
        return "redirect:/list";  // 수정 후 회원 리스트로 리다이렉트
    }

    // 사용자 삭제
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteById(userId);  // 사용자 삭제
        return "redirect:/list";  // 삭제 후 회원 리스트로 리다이렉트
    }
}

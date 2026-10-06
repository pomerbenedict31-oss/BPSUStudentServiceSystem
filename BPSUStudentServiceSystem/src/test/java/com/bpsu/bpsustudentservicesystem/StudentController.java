package com.bpsu.bpsustudentservicesystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String studentId,
            @RequestParam String password,
            Model model) {

        email = email.trim();
        studentId = studentId.trim();
        password = password.trim();

        if (!email.toLowerCase().endsWith("@bpsu.edu.ph")) {
            model.addAttribute("error",
                    "Please use a valid BPSU email.");
            return "login";
        }

        if (!studentId.matches("^\\d{2}-\\d{5}$")) {
            model.addAttribute("error",
                    "Student ID must follow the format 00-00000.");
            return "login";
        }

        if (!password.equals(studentId)) {
            model.addAttribute("error",
                    "Password must be the same as your Student ID.");
            return "login";
        }

        model.addAttribute("studentId", studentId);
        model.addAttribute("email", email);

        return "dashboard";
    }
}
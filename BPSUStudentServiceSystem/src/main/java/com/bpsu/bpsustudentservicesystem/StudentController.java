package com.bpsu.bpsustudentservicesystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("studentId") String studentId, @RequestParam("password") String password, Model model, HttpSession session) {

        email = email.trim();
        studentId = studentId.trim();
        password = password.trim();

        if (!email.toLowerCase().endsWith("@bpsu.edu.ph")) {
            model.addAttribute("error", "Please use a valid BPSU email.");
            return "login";
        }

        if (!studentId.matches("\\d{2}-\\d{5}")) {
            model.addAttribute("error", "Student ID must follow the format 00-00000.");
            return "login";
        }

        if (!password.equals(studentId)) {
            model.addAttribute("error", "Password must be the same as your Student ID.");
            return "login";
        }

        session.setAttribute("studentId", studentId);
        session.setAttribute("email", email);

        model.addAttribute("studentId", studentId);
        model.addAttribute("email", email);

        return "dashboard";
    }

    @GetMapping("/student/history")
    public String studentHistory(Model model, HttpSession session) {

        String studentId =
                (String) session.getAttribute("studentId");

        if (studentId == null) {
            return "redirect:/";
        }

        model.addAttribute("studentId", studentId);

        model.addAttribute("applications", ApplicationController.getApplications().stream().filter(application -> application.getStudentId().equals(studentId)).toList());

        return "student-history";
    }

    @GetMapping("/student/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}
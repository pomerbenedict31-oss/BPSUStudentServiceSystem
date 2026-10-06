package com.bpsu.bpsustudentservicesystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StaffController {

    @GetMapping("/staff/login")
    public String staffLogin() {
        return "staff-login";
    }

    @PostMapping("/staff/login")
    public String loginStaff(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {

        username = username.trim();
        password = password.trim();

        if (username.equals("staff")
                && password.equals("admin123")) {

            model.addAttribute("applications", ApplicationController.getApplications());

            return "staff-dashboard";
        }

        model.addAttribute("error", "Invalid staff username or password.");

        return "staff-login";
    }

    @GetMapping("/staff/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("applications", ApplicationController.getApplications());

        return "staff-dashboard";
    }

    @PostMapping("/staff/application/approve")
    public String approveApplication(
            @RequestParam("applicationId") String applicationId) {

        for (ApplicationController.Application application
                : ApplicationController.getApplications()) {

            if (application
                    .getApplicationId()
                    .equals(applicationId)) {

                application.setStatus("APPROVED");
                break;
            }
        }

        return "redirect:/staff/dashboard";
    }

    @PostMapping("/staff/application/reject")
    public String rejectApplication(@RequestParam("applicationId") String applicationId) {

        for (ApplicationController.Application application
                : ApplicationController.getApplications()) {

            if (application.getApplicationId().equals(applicationId)) {

                application.setStatus("REJECTED");
                break;
            }
        }

        return "redirect:/staff/dashboard";
    }

    @GetMapping("/staff/history")
    public String history(Model model) {

        model.addAttribute("applications", ApplicationController.getApplications());

        return "staff-history";
    }
}
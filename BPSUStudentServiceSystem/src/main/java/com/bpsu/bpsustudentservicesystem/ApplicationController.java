package com.bpsu.bpsustudentservicesystem;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ApplicationController {

    private static final List<Application> applications =
            new ArrayList<>();

    @GetMapping("/application")
    public String application(
            @RequestParam("office") String office,
            Model model,
            HttpSession session) {

        String studentId =
                (String) session.getAttribute("studentId");

        String email =
                (String) session.getAttribute("email");

        if (studentId == null || email == null) {
            return "redirect:/";
        }

        List<String> applicationTypes;

        switch (office) {

            case "Registrar":
                applicationTypes = List.of(
                        "Certificate of Enrollment",
                        "Certificate of Grades",
                        "Transcript of Records",
                        "Good Moral Certificate"
                );
                break;

            case "Cashier":
                applicationTypes = List.of(
                        "Assessment/Payment Concern",
                        "Payment Verification",
                        "Refund Request"
                );
                break;

            case "Guidance Counseling":
                applicationTypes = List.of(
                        "Counseling Request",
                        "Career Guidance",
                        "Personal/Academic Consultation"
                );
                break;

            case "Admission":
                applicationTypes = List.of(
                        "Admission Inquiry",
                        "Admission Requirements",
                        "Application Concern"
                );
                break;

            case "Faculty Consultation":
                applicationTypes = List.of(
                        "Faculty Consultation Request",
                        "Academic Consultation"
                );
                break;

            case "Student Affairs":
                applicationTypes = List.of(
                        "Student Concern",
                        "Student Organization Concern",
                        "Student Activity Request"
                );
                break;

            case "Shifting Office":
                applicationTypes = List.of(
                        "Shifting Inquiry",
                        "Shifting Application",
                        "Shifting Requirements"
                );
                break;

            default:
                applicationTypes = List.of();
        }

        model.addAttribute("office", office);
        model.addAttribute("studentId", studentId);
        model.addAttribute("email", email);
        model.addAttribute("applicationTypes", applicationTypes);

        return "application";
    }

    @PostMapping("/application/submit")
    public String submitApplication(
            @RequestParam("office") String office,
            @RequestParam("applicationType") String applicationType,
            @RequestParam("purpose") String purpose,
            Model model,
            HttpSession session) {

        String studentId =
                (String) session.getAttribute("studentId");

        String email =
                (String) session.getAttribute("email");

        if (studentId == null || email == null) {
            return "redirect:/";
        }

        String applicationId =
                "APP-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();

        LocalDateTime now = LocalDateTime.now();

        String date =
                now.format(
                        DateTimeFormatter.ofPattern(
                                "MMMM dd, yyyy"
                        )
                );

        String time =
                now.format(
                        DateTimeFormatter.ofPattern(
                                "hh:mm a"
                        )
                );

        String status = "PENDING";

        Application application =
                new Application(
                        applicationId,
                        studentId,
                        email,
                        office,
                        applicationType,
                        purpose,
                        date,
                        time,
                        status
                );

        applications.add(application);

        model.addAttribute("application", application);

        model.addAttribute("applicationId", applicationId);
        model.addAttribute("studentId", studentId);
        model.addAttribute("email", email);
        model.addAttribute("office", office);
        model.addAttribute("applicationType", applicationType);
        model.addAttribute("purpose", purpose);
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("status", status);

        return "application-success";
    }

    public static List<Application> getApplications() {
        return applications;
    }

    public static class Application {

        private String applicationId;
        private String studentId;
        private String email;
        private String office;
        private String applicationType;
        private String purpose;
        private String date;
        private String time;
        private String status;

        public Application(
                String applicationId,
                String studentId,
                String email,
                String office,
                String applicationType,
                String purpose,
                String date,
                String time,
                String status) {

            this.applicationId = applicationId;
            this.studentId = studentId;
            this.email = email;
            this.office = office;
            this.applicationType = applicationType;
            this.purpose = purpose;
            this.date = date;
            this.time = time;
            this.status = status;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getEmail() {
            return email;
        }

        public String getOffice() {
            return office;
        }

        public String getApplicationType() {
            return applicationType;
        }

        public String getPurpose() {
            return purpose;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
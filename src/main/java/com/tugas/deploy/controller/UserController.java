package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final String MY_NIM = "20240140064";
    private final String MY_NAME = "WULANDHIKA KURNALIAWATI";

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        if ("admin".equals(username) && MY_NIM.equals(password)) {
            session.setAttribute("user", username);
            // Buat list data mahasiswa di session (temporary)
            if (session.getAttribute("dataMahasiswa") == null) {
                session.setAttribute("dataMahasiswa", new ArrayList<User>());
            }
            return "redirect:/home";
        }
        return "redirect:/login?error";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("nim", MY_NIM);
        model.addAttribute("nama", MY_NAME);
        List<User> dataMahasiswa = (List<User>) session.getAttribute("dataMahasiswa");
        model.addAttribute("dataMahasiswa", dataMahasiswa);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/add")
    public String addMahasiswa(@RequestParam String nama,
                               @RequestParam String nim,
                               @RequestParam String jenisKelamin,
                               HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        List<User> dataMahasiswa = (List<User>) session.getAttribute("dataMahasiswa");
        dataMahasiswa.add(new User(nama, nim, jenisKelamin));
        session.setAttribute("dataMahasiswa", dataMahasiswa);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
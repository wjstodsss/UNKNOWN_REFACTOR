package com.unknown.paldak.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminErrorController {

    @GetMapping("/admin/error")
    public String handleError(Model model) {
        return "/admin/error"; // ???? ???´ì? ë·°ì?? ?´ë?
    }
}
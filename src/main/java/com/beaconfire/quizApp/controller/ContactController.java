package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.domain.Contact;
import com.beaconfire.quizApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute("contact") Contact contact, Model model) {
        contactService.saveContact(contact);
        model.addAttribute("success", "Message submitted successfully!");
        return "contact";
    }
}

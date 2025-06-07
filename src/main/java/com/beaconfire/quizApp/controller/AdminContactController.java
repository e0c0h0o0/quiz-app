package com.beaconfire.quizApp.controller.admin;

import com.beaconfire.quizApp.dao.ContactDAO;
import com.beaconfire.quizApp.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/contacts")
public class AdminContactController {

    private final ContactDAO contactDAO;

    @Autowired
    public AdminContactController(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }


    @GetMapping
    public String viewAllContacts(Model model) {
        List<Contact> contacts = contactDAO.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "adminContact";
    }

    @GetMapping("/view")
    public String viewContactDetail(@RequestParam("id") int id, Model model) {
        Contact contact = contactDAO.getContactById(id);
        model.addAttribute("contact", contact);
        return "contactView";
    }
}

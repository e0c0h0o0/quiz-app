package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.dao.ContactDAO;
import com.beaconfire.quizApp.domain.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public void saveContact(Contact contact) {
        contactDAO.save(contact);
    }
}

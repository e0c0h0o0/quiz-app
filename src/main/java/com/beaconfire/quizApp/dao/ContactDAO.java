package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Contact;

import java.util.List;


public interface ContactDAO {
    void save(Contact contact);
    List<Contact> getAllContacts();       // 新增
    Contact getContactById(int id);
}

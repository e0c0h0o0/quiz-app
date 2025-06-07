package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void save(Contact contact) {
        String sql = "INSERT INTO Contact (subject, message, email, time) VALUES (?, ?, ?, NOW())";
        jdbcTemplate.update(sql,
                contact.getSubject(),
                contact.getMessage(),
                contact.getEmail()
        );
    }

    @Override
    public List<Contact> getAllContacts() {
        String sql = "SELECT * FROM Contact ORDER BY time DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class));
    }

    @Override
    public Contact getContactById(int id) {
        String sql = "SELECT * FROM Contact WHERE contact_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Contact.class), id);
    }
}

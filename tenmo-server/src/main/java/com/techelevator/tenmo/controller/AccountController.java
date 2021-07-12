package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private JdbcAccountDao dao;
    private JdbcUserDao userDao;

    public AccountController(JdbcAccountDao dao, JdbcUserDao userDao){
        this.dao = dao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public double getBalance(@PathVariable int userId){
        return dao.getBalance(userId);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAll(){
        return userDao.findAll();
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> AllAccounts(){
        return dao.findAllAccounts();
    }




}

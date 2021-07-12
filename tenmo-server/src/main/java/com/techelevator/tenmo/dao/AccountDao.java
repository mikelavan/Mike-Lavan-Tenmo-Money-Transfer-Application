package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {

     double getBalance(int userId);

     Account findAccount(int userId);

     double subtractBalance(int accountId, double amount);

     double addBalance(int accountId, double amount);
}

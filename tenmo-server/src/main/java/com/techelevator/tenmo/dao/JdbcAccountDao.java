package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}
    @Override
    public double getBalance(int userId) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        double balance = 0.0;
        try {
            if (result.next()) {
                balance = result.getDouble("balance");
            }
        } catch (DataAccessException ex){
            System.out.println(ex.getMessage());
        }


            return balance;
    }

    @Override
    public Account findAccount(int userId) {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()){
            account = mapRowToAccount(result);
        }


        return account;
    }

    @Override
    public double subtractBalance(int userId, double amount) {
        Account account = findAccount(userId);

        double newBalance = account.getBalance() - amount;
        String  sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, newBalance, userId);
        return account.getBalance();
    }

    @Override
    public double addBalance(int userId, double amount) {
        Account account = findAccount(userId);

        double newBalance = account.getBalance() + amount;
        String  sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, newBalance, userId);
        return account.getBalance();

    }


    public List<Account> findAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM accounts;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;

    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getDouble("balance"));
        return account;
    }
}


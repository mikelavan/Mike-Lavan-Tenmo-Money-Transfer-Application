package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransfersDao implements TransfersDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcAccountDao jdbcAccountDao;

    public JdbcTransfersDao(JdbcTemplate jdbcTemplate, JdbcAccountDao jdbcAccountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAccountDao = jdbcAccountDao;
    }

    // todo consider returning a string based on if transaction is successful
    @Override
    public void sendTransfer(int accountFrom, int accountTo, double amount) {
        Transfers transfers = null;
        if (jdbcAccountDao.getBalance(accountFrom) > amount && amount > 0 && accountFrom != accountTo) {
            String sql = "INSERT INTO transfers " +
                    "(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), " +
                    "(SELECT account_id FROM accounts WHERE user_id = ?), ?)";
            jdbcTemplate.update(sql, accountFrom, accountTo, amount);
            jdbcAccountDao.addBalance(accountTo, amount);
            jdbcAccountDao.subtractBalance(accountFrom, amount);

        }


    }

    @Override
    public List<Transfers> getAllTransfers(int userId) {
        List<Transfers> transfersList = new ArrayList<>();

        String sql = "SELECT t.* FROM transfers t " +
        "JOIN accounts a ON (a.account_id = t.account_from) OR (a.account_id = t.account_to) " +
        "JOIN users u ON u.user_id = a.user_id " +
        "WHERE u.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Transfers transfers = mapRowToTransfers(results);
            transfersList.add(transfers);
        }
        return  transfersList;


    }

    @Override
    public Transfers getByTransferId(int transferId) {
        Transfers transfers = null;
        String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfers = mapRowToTransfers(results);
        }
        return transfers;
    }
    @Override
    public Transfers requestTransfer(int accountFrom, int accountTo, double amount) {
        return null;
    }

    @Override
    public Transfers approveTransfer(int accountFrom, int accountTo, double amount) {
        return null;
    }

    @Override
    public Transfers denyTransfer(int accountFrom, int accountTo, double amount) {
        return null;
    }

    @Override
    public List<Transfers> pendingTransfers(int userId) {
        return null;
    }

    private Transfers mapRowToTransfers(SqlRowSet rowSet) {
        Transfers transfers = new Transfers();
        transfers.setTransferId(rowSet.getInt("transfer_id"));
        transfers.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfers.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfers.setAccountFrom(rowSet.getInt("account_from"));
        transfers.setAccountTo(rowSet.getInt("account_to"));
        transfers.setAmount(rowSet.getDouble("amount"));
        return transfers;
    }
}

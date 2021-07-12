package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransferController {
    private JdbcTransfersDao transfersDao;
    private JdbcAccountDao accountDao;
    private JdbcUserDao userDao;


    public TransferController(JdbcTransfersDao transfersDao, JdbcAccountDao accountDao, JdbcUserDao userDao) {
        this.transfersDao = transfersDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping (path = "/transfers", method = RequestMethod.POST)
    public void sendTransfer(@RequestBody Transfers transfers) {
        transfersDao.sendTransfer(transfers.getAccountFrom(), transfers.getAccountTo(), transfers.getAmount());

    }

    @RequestMapping (path = "/transfers/users/{userId}", method = RequestMethod.GET)
    public List<Transfers> getAllTransfers(@PathVariable int userId){
        return transfersDao.getAllTransfers(userId);
    }

    @RequestMapping (path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfers getTransferById(@PathVariable int transferId){
        return transfersDao.getByTransferId(transferId);
    }



}



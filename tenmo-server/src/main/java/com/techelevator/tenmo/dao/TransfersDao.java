package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.util.List;

public interface TransfersDao {

    void sendTransfer(int accountFrom, int accountTo, double amount);

    List<Transfers> getAllTransfers (int userId);

    Transfers getByTransferId (int transferId);

    Transfers requestTransfer (int accountFrom, int accountTo, double amount);

    Transfers approveTransfer (int accountFrom, int accountTo, double amount);

    Transfers denyTransfer (int accountFrom, int accountTo, double amount);

    List<Transfers> pendingTransfers (int userId);

}

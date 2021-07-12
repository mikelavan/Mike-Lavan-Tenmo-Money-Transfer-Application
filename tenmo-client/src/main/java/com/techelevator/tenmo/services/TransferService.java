package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import io.cucumber.java.eo.Do;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Scanner;

public class TransferService {
    private String baseUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();
    private AccountService accountService;

    public TransferService (String url, AuthenticatedUser currentUser){
        this.baseUrl = url;
        this.currentUser = currentUser;
        this.restTemplate = restTemplate;
        this.accountService = new AccountService(url, currentUser);
    }



    public User[] listUsers(){
        User[] users = null;
        try {
            ResponseEntity<User[]> responseEntity =
                    restTemplate.exchange(baseUrl + "/users", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = responseEntity.getBody();
        } catch (RestClientResponseException ex){
            System.out.println(ex.getMessage());
        }
        if(users.length != 0) {
            for (User user: users) {
                System.out.println(user.toString());
            }
        }
        return users;
    }

//    public int promptForUsers(User[] users, String action) {
//        int menuSelection;
//        System.out.println("-------------------------------------------");
//        System.out.println("Users");
//        System.out.println("ID               Name");
//        System.out.println("-------------------------------------------");
//
//    }

    public void sendBucks() {

        Scanner scanner = new Scanner(System.in);
        User[] users = listUsers();
        Transfers transfer = new Transfers();


        listUsers();
        System.out.println("Enter ID of user you are sending to: ");
        String string = scanner.nextLine();
        try {
            for (User user: users) {
                if (string.equals(user.getId().toString())) {
                    transfer.setAccountTo(user.getId());
                }
            }
        } catch(IllegalArgumentException ex) {
            System.out.println("Invalid response");
        }

        transfer.setAccountFrom(currentUser.getUser().getId());

        System.out.println("Enter amount: ");
        String stringAmount = scanner.nextLine();
        try {
            if (Double.parseDouble(stringAmount) > accountService.getBalance()) {
                System.out.println("That's to much dude, you can't be sending that!");
            } else
            transfer.setAmount(Double.parseDouble(stringAmount));
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid response, please try again!");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfers> entity = new HttpEntity<>(transfer, headers);
        try {
            transfer = restTemplate.postForObject(baseUrl + "/transfers", entity, Transfers.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getMessage());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public Transfers[] viewTransferHistory() {
        Transfers [] transfers = null;
        try{
            transfers = restTemplate.getForObject(baseUrl + "/transfers/users/" + currentUser.getUser().getId(), Transfers[].class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getMessage());
        }catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());

        } if (transfers.length != 0) {
            for (Transfers transfer : transfers) {
                System.out.println(transfer.toString());
             }
            }
        return transfers;
    }





    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}

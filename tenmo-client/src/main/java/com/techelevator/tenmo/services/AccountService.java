package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


public class AccountService {
    private String baseUrl;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();


    public AccountService(String url, AuthenticatedUser currentUser){
        this.currentUser = currentUser;
        this.baseUrl = url;
        this.restTemplate = restTemplate;
    }

    public double getBalance(){
        Double balance = null;
        try{
            balance = restTemplate.exchange(baseUrl + "balance/" + currentUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), Double.class).getBody();
            System.out.println("Your current account balance is: $" + balance);
        } catch (RestClientResponseException ex){
            System.out.println(ex.getMessage());
        }
        return balance;
}

private HttpEntity makeAuthEntity(){
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(currentUser.getToken());
    HttpEntity entity = new HttpEntity(headers);
    return entity;
}

}

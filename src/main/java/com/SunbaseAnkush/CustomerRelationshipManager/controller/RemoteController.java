package com.SunbaseAnkush.CustomerRelationshipManager.controller;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.Customer;
import com.SunbaseAnkush.CustomerRelationshipManager.model.LoginCredentials;
import com.SunbaseAnkush.CustomerRelationshipManager.service.ApiService;
import com.SunbaseAnkush.CustomerRelationshipManager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("remoteController")
public class RemoteController {

    @Autowired
    private CustomerService customerService;

    private static final String LOGIN_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private static final String DATA_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";
    private String cmd = "get_customer_list";
    ApiService apiService = new ApiService();
    @GetMapping("/getToken")
    public String getToken(@RequestBody LoginCredentials loginCredentials) {
//        String requestBody = "{ \"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\" }";

        String requestBody = String.format("{ \"login_id\": \"%s\", \"password\": \"%s\" }", loginCredentials.getLogin_id(), loginCredentials.getPassword());
        String token = apiService.callLoginApi(LOGIN_URL, requestBody);
        return token;
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity get_Customer_list(@RequestParam String cmd){
        List<Customer> customerList = apiService.callGetAllCustomersApi(DATA_URL, cmd);
        if (customerList.isEmpty()) {
            return new ResponseEntity<>("No new data to update", HttpStatus.NO_CONTENT);
        }
        customerService.addReceivedData(customerList);
        return new ResponseEntity<>("Data Added/Updated successfully", HttpStatus.OK);
    }

}

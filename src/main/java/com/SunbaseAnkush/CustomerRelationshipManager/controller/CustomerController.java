package com.SunbaseAnkush.CustomerRelationshipManager.controller;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.AuthRequest;
import com.SunbaseAnkush.CustomerRelationshipManager.entity.Customer;
import com.SunbaseAnkush.CustomerRelationshipManager.entity.UserInfo;
import com.SunbaseAnkush.CustomerRelationshipManager.service.CustomerService;
import com.SunbaseAnkush.CustomerRelationshipManager.service.JwtService;
import com.SunbaseAnkush.CustomerRelationshipManager.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String LOGIN_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";


// Tested for initialization of project and was working
//    @GetMapping("/test")
//    public String testing() {
//        return "Hello world!";
//    }

    //Login authentication (older)
//    @PostMapping("/login")
//    public ResponseEntity loginAuthentication(@RequestBody UserInfo loginCredentials) {
//        try {
//            String authorization = customerService.checkAuthorization(loginCredentials);
//            return new ResponseEntity<>("Login successful", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo) {
        try {
            return userInfoService.addUser(userInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public String addUser(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GetMapping("/getUsers")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUser/{id}")
    public UserInfo getUser(@PathVariable Integer id) {
        return userInfoService.getUser(id);
    }


    // create a new customer in Database
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.createNewCustomer(customer);
            if(savedCustomer == null) {
                return new ResponseEntity<>("Please fill all the details", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("New Customer created successfully", HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get details of a customer by uuid
    @GetMapping("/get-a-customer-details-by-ID/{uuid}")
    public ResponseEntity<String> getDetailsOfACustomerById(@PathVariable("uuid") String uuid) {
        try {
            Customer savedCustomer = customerService.getCustomerById(uuid);
            if (savedCustomer == null) {
                return new ResponseEntity<>("Customer doesn't exists with UUID: " + uuid, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Details of the existing customer: " + savedCustomer, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get details of all Customers exists in the Database
    @GetMapping("/get-all-customers-details")
    public ResponseEntity getAllCustomersDetails() {
        try {
            List<Customer> customerList = customerService.getAllCustomers();
            if (customerList == null) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // Update a customer
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestParam String uuid, @RequestBody Customer modifiedCustomer) {
        try {
//            if (modifiedCustomer.getFirst_name().isBlank() || modifiedCustomer.getLast_name().isBlank() || modifiedCustomer.getStreet().isBlank()
//                    || modifiedCustomer.getAddress().isBlank() || modifiedCustomer.getCity().isBlank() || modifiedCustomer.getState().isBlank()
//                    || modifiedCustomer.getEmail().isBlank() || modifiedCustomer.getPhone().isBlank()) {
//                return new ResponseEntity<>("Please fill all details of customer to update", HttpStatus.BAD_REQUEST);
//            }
            Optional<Customer> updatedCustomer = customerService.updateCustomer(uuid, modifiedCustomer);
            if (updatedCustomer.isEmpty()) {
                return new ResponseEntity<>("UUID is Incorrect", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Customer details has been updated", HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a customer by uuid
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String uuid) {

        try {
            if (uuid == null) {
                return new ResponseEntity<>("UUID is not provided", HttpStatus.BAD_REQUEST);
            }
            Boolean customerDeleted = customerService.deleteCustomer(uuid);
            if (!customerDeleted) {
                return new ResponseEntity<>("Customer doesn't exists with UUID: " + uuid, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Customer Deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error: " + ex, HttpStatus.BAD_REQUEST);
        }
    }

}

package com.SunbaseAnkush.CustomerRelationshipManager.service;

import com.SunbaseAnkush.CustomerRelationshipManager.entity.Customer;
import com.SunbaseAnkush.CustomerRelationshipManager.entity.UserInfo;
import com.SunbaseAnkush.CustomerRelationshipManager.repository.UserInfoRepository;
import com.SunbaseAnkush.CustomerRelationshipManager.repository.CustomerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String checkAuthorization(UserInfo loginCredentials) throws Exception {
        if(loginCredentials.getLoginId().isBlank() || loginCredentials.getPassword().isBlank()) {
            throw new Exception("LoginId or Password is empty");
        }
        Optional<UserInfo> admin = userInfoRepository.findById(loginCredentials.getId());
        if (admin.isEmpty()) {
            throw new Exception("Invalid LoginId or Password");
        }
        return "Login Successful";
    }



    public Customer createNewCustomer(Customer customer) throws Exception {
            Customer savedCustomer = null;
            if (customer.getFirst_name().isBlank() || customer.getLast_name().isBlank() || customer.getStreet().isBlank()
                    || customer.getAddress().isBlank() || customer.getCity().isBlank() || customer.getState().isBlank()
                    || customer.getEmail().isBlank() || customer.getPhone().isBlank()) {
                throw new Exception("Unable to create new customer" + "\n" + "Some details are missing");
            }
            savedCustomer = customerInfoRepository.save(customer);
            return savedCustomer;
    }


    public Customer getCustomerById(String uuid) throws Exception {
            Optional<Customer> savedCustomer = customerInfoRepository.findById(uuid);
            if (savedCustomer.isEmpty()) {
                throw new Exception("Customer doesn't exists with the input uuid");
            }
            return savedCustomer.get();
    }


    public List<Customer> getAllCustomers() throws Exception {
            List<Customer> listOfCustomers = customerInfoRepository.findAll();
            if (listOfCustomers.isEmpty()) {
                throw new Exception("No customer present in database");
            }
            return listOfCustomers;
    }


    public Optional<Customer> updateCustomer(String uuid, Customer modifiedCustomer) throws Exception {
            Optional<Customer> existingCustomer = customerInfoRepository.findById(uuid);
            if (existingCustomer.isEmpty()) {
                throw new Exception("Customer is not present with uuid:" + uuid);
            }
            Customer customer = existingCustomer.get();
            customer.setFirst_name(modifiedCustomer.getFirst_name());
            customer.setLast_name(modifiedCustomer.getLast_name());
            customer.setStreet(modifiedCustomer.getStreet());
            customer.setAddress(modifiedCustomer.getAddress());
            customer.setCity(modifiedCustomer.getCity());
            customer.setState(modifiedCustomer.getState());
            customer.setEmail(modifiedCustomer.getEmail());
            customer.setPhone(modifiedCustomer.getPhone());
            customerInfoRepository.save(customer);
            Optional<Customer> updatedCustomer = customerInfoRepository.findById(uuid);

            return updatedCustomer;
    }


    public Boolean deleteCustomer(String uuid) throws Exception {
            Optional<Customer> customer = customerInfoRepository.findById(uuid);
            if (customer.isEmpty()) {
                throw new Exception("Unable to delete customer with input uuid" + "\n" + "Check uuid again");
            }
            customerInfoRepository.deleteById(uuid);
            return true;
    }


    public String createAdmin(UserInfo newAdmin) throws Exception {
        UserInfo admin = userInfoRepository.save(newAdmin);
        if (admin == null) {
            throw new Exception("Unable to create new Admin");
        }
        return "New admin created successfully";
    }


    public void addReceivedData(List<Customer> customerList) {
        for (Customer customer : customerList) {
            customerInfoRepository.save(customer);
        }
    }
}

# Spring Boot Customer CRUD API

This Spring Boot project provides a RESTful API for managing customer details with CRUD operations. It uses JWT for authentication, Spring Security for securing endpoints, and MySQL as the database.

## Table of Contents
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Usage](#usage)


## Features

- Create, Read, Update, and Delete customer details.
- JWT-based authentication for secure API access.
- Role-based access control for different user roles.
- Spring Security for securing endpoints.
- Spring MVC architecture for clean and modular code.
- MySQL database for persistent storage.

## API Endpoints
- [Login:](#login) /auth/login
- [Get All Customer:](#users) /auth/getUsers
- [Add Users:](#addUser) /auth/addUser
- [Get Details of All Customers:](#allCustomers) /auth/get-all-customers-details
- [Update details of A Customer:](#update) /auth/update
- [Delete A Customer:](#delete) /auth/delete

## Authentication
- JWT based authentication.
- Token gets generated after hitting Login API with Registered user LoginId and password
- Generated Token has to be used as Bearer Token in Authentication header for further API calls

## Usage
- To monitor registered customers' details
- Update/delete details of a customer

## Frontend Interface with Implementation
- Frontend developed with ReactJS
- Github address: https://github.com/Ankush-nitjsr/customer-relationship-Manager-reactJS


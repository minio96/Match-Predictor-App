# Match-Predictor-App

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Authentication](#authentication)
* [Setup](#setup)

## General info
App for predicting match results and competing with other players

## Technologies
- Spring 
- Thymeleaf
- Hibernate
- Postgres
- Lombok
- Docker

## Authentication
Authentication to web app provided by using username/password for each user.

Token to WorldCup Api (https://github.com/rezarahiminia/free-api-worldcup2022) retrieved by authenticating via `/user/login` 

## Setup
To run this project make sure maven is installed. 
Navigate to root of the project and run

`mvn spring-boot:run`

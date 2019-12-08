#!/bin/sh

mvn clean test
allure serve target/allure-results -p 5000
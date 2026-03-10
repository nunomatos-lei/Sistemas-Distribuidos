# Sistemas Distribuídos

This project was developed as a collaborative effort during my Bachelor's degree. It simulates a distributed reservation system where users select "menus" and pay to confirm bookings, in case of cancel money is refunded and seats are liberated.

## Architecture
The system consists of **4 microservices** communicating via a hybrid approach, some using only one type of communication:
* **Synchronous:** communication via REST using **OpenFeign**.
* **Asynchronous:** Event-driven architecture powered by **Apache Kafka**.
* **Framework:** Built with **Spring Boot**.

## Technologies
* **Backend:** Java / Spring Boot
* **Communication:** OpenFeign, Apache Kafka
* **Containerization:** Docker (used for environment orchestration and easy setup)
* **Build Tool:** Maven/Gradle

## Team
This project was a joint effort. We were a pair in every project that semester so it ensured a balanced split of tasks and helped us plan the project. Originally, each service was managed in its own repository, now consolidated here for demonstration.
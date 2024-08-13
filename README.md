# Survey Management System

#Problem Description

Develop a survey management system using microservices architecture using rest API. Surveys are question and Answers (Supports options(multiple/single options) and/or free form response).

Functional requirements

Administrator Use Case:

The system should allow the administrator to create (setup) surveys.

Support for different versions of same Surveys(Same survey will have different versions at the same time).

Customer Use Case (can be valid login as well as anonymous) :

a) The system should allow users to access and answers the survey Questionaire. b) The system should allow users to resume surveys, where they have left before (Not mandatory for anonymous users) c) The users should be given provision to continue on a version if they have started responding to survey but not finished. d) New users with no in-progress surveys will be given the latest version of survey. Few non-functional requirements are

• There should be sufficient test coverage (unit / integration tests) and code quality/security checks. • Service(s) should be observable, it should have sufficient logs and metrics • Service(s) should be containerized. • Automate all stages within a CI/CD pipeline • Architecture diagram and sequence diagram. . Survey UI as described in the Functional spec above [ Creation/ design of survey] Optional for Backend developers Build & deployment script for for the application as microservice. Required Artifacts

Source code Unit /Integration tests Docker/K8 files Build scripts

# Overview
The Survey Management System was build using spring boot microservice architecture for the backend and angular for the frontend  

# Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)

# Features
**Admin Functions**
- **Survey Creation:** - Allows administrators to design and launch new surveys with customizable questions and formats.
- **Survey Version Creation:** - Enables the creation of multiple versions of a survey to accommodate updates, **LATEST VERSION WILL DISPLAYED**
  
**User Functions**
- **User Auth** Provides basic authentication for users to access and participate in surveys or respond anonymously.
- **User Response** Facilitates user responses to surveys, ensuring data is collected efficiently and accurately.

# Installation

## Install with docker

**Backend**
1. [OPTIONAL] **Config to delay timeout on windows, if docker connection is slow**
    ```bash
    export DOCKER_CLIENT_TIMEOUT=300
    export COMPOSE_HTTP_TIMEOUT=300
    ```

2. **Run Docker compose from the docker folder**
   ```bash
    docker-compose up -d
    ```

**Frontend**
1. **Required Node.js v20.10.0 and Angular CLI: 17.3.8**
    
2. **Install dependencies:**

    ```bash
    npm install
    ```
3. **Start the development server:**

    ```bash
    npm start
    ```

## Install without docker

**Backend**
1. **Required JDK17**
   
2. **Set up databases for user-service, survey-creator-service, survey-respondant service**

3.  **Run the dicovery client before ruuning any service to register**

**Frontend**
1. **Required Node.js v20.10.0 and Angular CLI: 17.3.8**
    
2. **Install dependencies:**

    ```bash
    npm install
    ```
3. **Start the development server:**

    ```bash
    npm start
    ```
# Usage

1. **Access the application:**
    Open your web browser and navigate to `http://localhost:4200`.

2. **Default port for services**
   
| Service                   | Port  |
|---------------------------|-------|
| User Service              | 8080  |
| Survey Creator Service    | 8081  |
| Survey Respondent Service | 8082  |
| Survey Respondent Service | 8761  |

    

## Technologies Used

- **Frontend:** Angular, Bootstrap
- **Backend:** Java, Springboot
- **Database:** Mysql
- **Deployment:** Docker, Docker Hub

#Screenshots

##Landing screen
<div align="center">
  <img height="200" src="https://raw.githubusercontent.com/oracle-osvc-ext/nkramachandran21/main/screenshots/landing.jpg?token=GHSAT0AAAAAACSTXS2TOV55DXMMHGQ3UAKOZT6UIXQ"  />
</div>

##Creator Dashboard
<div align="center">
  <img height="200" src="https://raw.githubusercontent.com/oracle-osvc-ext/nkramachandran21/main/screenshots/creator-dashboard.jpg?token=GHSAT0AAAAAACSTXS2TEQ4IFW6BJUZHFTEUZT6UD3A"  />
</div>

##Creator Survey Screen
<div align="center">
  <img height="200" src="https://raw.githubusercontent.com/oracle-osvc-ext/nkramachandran21/main/screenshots/creator-survey-editor.jpg?token=GHSAT0AAAAAACSTXS2TKU2HZ5LMSWQFOGVWZT6UEPA"  />
</div>

##Respondant Auth
<div align="center">
  <img height="200" src="https://raw.githubusercontent.com/oracle-osvc-ext/nkramachandran21/main/screenshots/respondant-auth.jpg?token=GHSAT0AAAAAACSTXS2SFXNWNLE6ES5AMLOWZT6UFHQ"  />
</div>

##Respondant Dashboard
<div align="center">
  <img height="200" src="https://raw.githubusercontent.com/oracle-osvc-ext/nkramachandran21/main/screenshots/respondant-wall.jpg?token=GHSAT0AAAAAACSTXS2SGNLXKDSTCM5OB32OZT6UF6Q"  />
</div>

##Respondant Survey Screen
<div align="center">
  <img height="200" src="https://raw.githubusercontent.com/oracle-osvc-ext/nkramachandran21/main/screenshots/respondant-wall.jpg?token=GHSAT0AAAAAACSTXS2TXCI7BGKIPBUXYDR2ZT6UGZA"  />
</div>


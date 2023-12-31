# movielist

Movielist is a full-stack Java website application developed using Spring Boot. It allows users to search for movies from the TMDb database, rate them, and build a list of favorite movies. The app ranks the favorite movies based on the user's scores, providing a personalized list of top-rated films. Additionally, there is a selenium based cronjob script that scans local cinemas for the user's favourite movies - this will send out an email each night telling the user if one of their favourite movies are showing near them!

Deployed fully with render.com at https://movielist-render-deployment.onrender.com

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)

## Features

- **Search Movies:** Utilize the TMDb database to search for movies.
- **Rate Movies:** Assign scores to movies and build a list of favorite films.
- **Ranking System:** Automatically rank favorite movies based on user scores.
- **Cinema Notifications:** Receive notifications when favorite movies are showing in nearby cinemas.

## Technologies

- **Backend:**

  - Spring Boot
  - Java
  - Docker

- **Frontend:**

  - HTML
  - CSS
  - JavaScript
  - Thymeleaf

- **Database:**

  - PostgreSQL

- **3rd party APIs:**

  - Google Places API - to search for cinemas near to the user
  - tMDB - movie database to provide data for the app

## Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/JackHenry-G/movielist.git
   cd movielist
   ```
2. **Setup postgreSQL**

   Before running the application, you need to setup a data source. You can do this using a PostgreSQL database, which I have provided within the docker-compose file.

   1. Ensure you have docker installed on your machine
   2. Set the correct profile in the parent 'application.properties' file:
      - Use 'test' if you want to have capabilities for quicker testing, like an automated register of a test user.
      - Use 'staging' if you want the production app but with a test database, but without test feature capabilities.
        Both of these include a docker postgresql intance set to 'create-drop' so the database will be cleared of data and setup a new when the app is restarted.
   3. docker-compose up -d
   4. ./mvnw spring-boot:run

   ![Docker setup][movielist_docker_setup.png]

3. **Access the app**
   Open your browser and navigate to http://localhost:8080

## Logs

The logs are configured via the application.properties file to do the below but feel free to change them to how you prefer!
I've set up the logs to only catch what's really needed, making them easy on the eyes with crucial info for better debugging. No more dealing with huge log files or drowning in heaps of old logs—I've got it streamlined for a log system that's a breeze to read and manage, making debugging a whole lot smoother.

1. Only logs at info level and above are logged.
2. Logs are directed to a file named "movielist.log"
3. There is a rolling policy, which creates a new log file when the current one reaches 10MB
4. The rolling policy also only retains two archived log files before deleting the oldest one
5. There is a custom output to make them easier to read

## Improvements

Improved security for API keys - currently the api keys are set explicitly within the code for everyone to see. This is fine for now as this application is just for test purposes and each account is a free tier, so there is little risk to me. However, in standard production environment it would be wise to secure these behind environment variables or encryption. Similarly, I have an API call within the JavaScript that I would refactor to be from the Spring Boot backend.

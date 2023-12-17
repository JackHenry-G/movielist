# movielist

Movielist is a full-stack Java website application developed using Spring Boot. It allows users to search for movies from the TMDb database, rate them, and build a list of favorite movies. The app ranks the favorite movies based on the user's scores, providing a personalized list of top-rated films. Additionally, there are plans to implement a feature that notifies users when their favorite movies are showing in nearby cinemas.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)

## Features

- **Search Movies:** Utilize the TMDb database to search for movies.
- **Rate Movies:** Assign scores to movies and build a list of favorite films.
- **Ranking System:** Automatically rank favorite movies based on user scores.
- **Cinema Notifications (Planned):** Receive notifications when favorite movies are showing in nearby cinemas.

## Technologies

- **Backend:**

  - Spring Boot
  - Java
  - PostgreSQL (Database)
  - TMDb API

- **Frontend:**

  - HTML
  - CSS
  - JavaScript
  - Thymeleaf

- **Database:**
  - PostgreSQL

## Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/JackHenry-G/movielist.git
   cd movielist
   ```
2. **Setup postgreSQL**
   Before running the application, you need to setup a data source. You can do this using a PostgreSQL database, either locally or using the
   dockerized one I provided as part of this project. Depending on your choice you will need to edit a few things.

   - Local PostgreSQL:

     - Setup a PostgreSQL server, ensuring to note down the username and password you used to enter it.
     - Setup a database named 'movielist'.
     - Open the 'application-local.properties' file in this project and change teh username and password appropiately.
     - Ensure that within 'application.properties', the active profile is set to 'local'
     - If the PostgreSQL server is running, the app should now connect without issues. Simply run the app:

     ````bash
        docker-compose up -d
        ./mvnw spring-boot:run
        ```

     ````

   - Docker (easier):
     - Make sure you have docker installed on your machine
     - - Ensure that within 'application.properties', the active profile is set to 'docker'
     - Run the following commands:
       `bash
      docker-compose up -d
      ./mvnw spring-boot:run
      `
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

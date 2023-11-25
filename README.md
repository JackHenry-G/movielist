# movielist

Movielist is a full-stack Java website application developed using Spring Boot. It allows users to search for movies from the TMDb database, rate them, and build a list of favorite movies. The app ranks the favorite movies based on the user's scores, providing a personalized list of top-rated films. Additionally, there are plans to implement a feature that notifies users when their favorite movies are showing in nearby cinemas.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Usage](#usage)

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
   Create a PostgreSQL database and update the app properties to the new database credentials.
3. **Setup TMDb API Key**
   Obtain a TMDb API key from TMDb Developer and replace YOUR_API_KEY in the application properties.
4. **Build and run**
   ./mvnw spring-boot:run
5. **Access the app**
   Open your browser and navigate to http://localhost:8080

## Usage

1. **Search for movies**
   Use the search functionality to search for movies from the TMDB database!
2. **Rate movies**
   After selecting a movie, provide a score to add it to your list!
3. **View rankings**
   View which movies are your favourite of all time!
4. **Cinema notifications (to come later)**
   Be notified when you're favourite ranked movies are in a cinema near you!

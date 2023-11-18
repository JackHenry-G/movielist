function deleteRow(movieid) {
  if (confirm("Are you sure you want to delete this movie from your list?")) {
    // make AJAX, asynchronous javascript, request to the stprng boot endpoint
    // to to delete the row
    // got to the specified endpoint with the ID as a path parameter
    fetch("/movies/" + movieid, {
      method: "DELETE", // specify it is a DELELTE CRUD type
    })
      .then((response) => {
        if (response.ok) {
          window.location.href = "/movies"; // if delete was successful, then refresh page by returning user to /movies URL  which will call the database to get whole list of movies again
        } else {
          throw new Error("Error deleting movie"); // if was failed, the throw error
        }
      })
      .catch((error) => {
        console.error(error);
        // Handle error case
      });
  }
}

package app.services;

import app.dtos.MovieDTO;
import app.entities.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private final ObjectMapper objectMapper;
    public MovieService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MovieDTO> getAllMoviesFromThisYear(String jsonFile, String uri) {
        List<MovieDTO> movies = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int currentPage = 1;
        int totalPages = 1; // Default value to ensure loop starts

        try {
            do {
                HttpClient client = HttpClient.newHttpClient();

                StringBuilder builder = new StringBuilder()
                        .append(uri)
                        .append("&api_key=")
                        .append(System.getenv("api_key"))
                        .append("&page=")
                        .append(currentPage);

                // Create a HTTP request
                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(new URI(builder.toString()))
                        .GET()
                        .build();

                // Send request and receive response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonNode rootNode = objectMapper.readTree(response.body());

                    // Get total_pages
                    JsonNode totalPagesNode = rootNode.get("total_pages");
                    if (totalPagesNode != null) {
                        totalPages = totalPagesNode.asInt();
                    } else {
                        System.out.println("Warning: 'total_pages' field is missing in the response.");
                    }

                    JsonNode jsonNode = rootNode.get(jsonFile);
                    if (jsonNode != null) {
                        // Iterate through the movies in the results
                        for (JsonNode node : jsonNode) {
                            MovieDTO movieDTO = objectMapper.treeToValue(node, MovieDTO.class);
                            if (movieDTO.getReleaseDate() != null && movieDTO.getReleaseDate().getYear() == year) {
                                movies.add(movieDTO);
                            }
                        }
                    } else {
                        System.out.println("Warning: No movies found in '" + jsonFile + "'.");
                    }

                    System.out.println((int) ((currentPage / (double) totalPages) * 100) + "% complete.");
                    currentPage++;
                } else {
                    System.out.println("GET request failed. Status code: " + response.statusCode());
                    break;
                }

            } while (currentPage <= totalPages);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }



}

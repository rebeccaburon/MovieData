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

    public List<MovieDTO> getDanishMoviesFromLastFiveYears(String jsonFile, String uri) {
        List<MovieDTO> movies = new ArrayList<>();
        LocalDate today = LocalDate.now();
        int currentPage = 1;
        int totalPages;
        try {
            do {
                HttpClient client = HttpClient.newHttpClient();
                //What the request needs to contain
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

                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode jsonNode = rootNode.get(jsonFile);

                totalPages = rootNode.get("total_pages").asInt();
                currentPage++;


                System.out.println((int) ((currentPage/(double) totalPages) *100) + "%");
                if (response.statusCode() == 200){
                for (JsonNode node : jsonNode){
                    MovieDTO movie = objectMapper.treeToValue(node, MovieDTO.class);
                    if(movie.getReleaseDate() !=null && movie.getReleaseDate().isAfter(LocalDate.now().minusYears(5))){
                        movies.add(movie);
                    }
                }
                } else {
                    System.out.println("GET request Faild. Status code: " + response.statusCode());
                }
                } while (currentPage <= totalPages);
            return movies;
            } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}

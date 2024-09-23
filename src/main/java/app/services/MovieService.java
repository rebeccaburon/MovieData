package app.services;

import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

public class MovieService {
    private final ObjectMapper objectMapper;

    public MovieService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MovieDTO> getDanishMoviesFromLastFiveYears(String jsonFile, String uri) {
        List<MovieDTO> movies = new ArrayList<>();
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

                //Showing loading process
                System.out.println((int) ((currentPage / (double) totalPages) * 100) + "%");
                if (response.statusCode() == 200) {
                    for (JsonNode node : jsonNode) {
                        MovieDTO movie = objectMapper.treeToValue(node, MovieDTO.class);
                        if (movie.getReleaseDate() != null && movie.getReleaseDate().isAfter(LocalDate.now().minusDays(10))) {
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
//fetch methods director, actor og genres


    // https://api.themoviedb.org/3/movie/249397/credits?api_key=a1562ad5e4748cc4c6b6c9522d56f32b

    public Set<GenreDTO> fethGenresByMovieID(int movieID, String jsonFile, String uri) {
        Set<GenreDTO> genreDTOS = new HashSet<>();
        int currentPage = 1;
        int totalPages;
        try {
            HttpClient client = HttpClient.newHttpClient();
            //What the request needs to contain
            StringBuilder builder = new StringBuilder()
                    .append(uri)
                    .append(movieID)
                    .append("&api_key=")
                    .append(System.getenv("api_key"))
                    .append("&page=")
                    .append(currentPage);

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(builder.toString()))
                    .GET()
                    .build();

            // Send request and receive response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode genreNode = rootNode.get("genres");

                for (JsonNode node : genreNode) {
                    GenreDTO genre = objectMapper.treeToValue(genreNode, GenreDTO.class);
                    genreDTOS.add(genre);
                }
            } else {
                System.out.println("GET request Faild. Status code: " + response.statusCode());
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return genreDTOS;
    }

    public Set<ActorDTO>fethActorsByJobTitel(String job, String jsonFile, String uri) {
        Set<ActorDTO> actorDTOSList = new HashSet<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            if(job.equals("Acting")){
                StringBuilder builder = new StringBuilder()
                        .append(job)
                        .append(uri)
                        .append("&api_key=")
                        .append(System.getenv("api_key"));

                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(new URI(builder.toString()))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if(response.statusCode() == 200) {
                    JsonNode rootNode = objectMapper.readTree(response.body());
                    JsonNode ActorNode = rootNode.get("Acting");
                    for (JsonNode node : ActorNode) {
                        ActorDTO actor = objectMapper.treeToValue(node,ActorDTO.class);
                        actorDTOSList.add(actor);
                    }
                } else {
                    System.out.println("GET request Faild. Status code: " + response.statusCode());
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return actorDTOSList;
    }
    public Set<DirectorDTO>fethDirectorByJobTitel(String job, String jsonFile, String uri) {
        Set<DirectorDTO> DirectorDTOSList = new HashSet<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            if(job.equals("Director")){
                StringBuilder builder = new StringBuilder()
                        .append(job)
                        .append(uri)
                        .append("&api_key=")
                        .append(System.getenv("api_key"));

                HttpRequest request = HttpRequest
                        .newBuilder()
                        .uri(new URI(builder.toString()))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if(response.statusCode() == 200) {
                    JsonNode rootNode = objectMapper.readTree(response.body());
                    JsonNode ActorNode = rootNode.get("Director");
                    for (JsonNode node : ActorNode) {
                        DirectorDTO director = objectMapper.treeToValue(node,DirectorDTO.class);
                        DirectorDTOSList.add(director);
                    }
                } else {
                    System.out.println("GET request Faild. Status code: " + response.statusCode());
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return DirectorDTOSList;
    }

}

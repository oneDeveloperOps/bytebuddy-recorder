package com.nfr.record.services;

import com.google.gson.Gson;
import com.nfr.record.entity.Post;
import com.nfr.record.interfaces.PostService;
import com.nfr.record.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public HashMap<String, Object> createPost(Post post) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://worldtimeapi.org/api/timezone/Asia/Kolkata"))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<?, ?> httpResponse = new Gson().fromJson(response.body(), HashMap.class);
        Post newPost = postRepository.save(post);
        HashMap<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("db_post", newPost);
        finalResponse.put("http_response", httpResponse);
        return finalResponse;
    }
}
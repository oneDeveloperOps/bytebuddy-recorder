package com.nfr.record.interfaces;

import com.nfr.record.entity.Post;

import java.util.HashMap;

public interface PostService {
    public HashMap<String, Object> createPost(Post post) throws Exception;
}

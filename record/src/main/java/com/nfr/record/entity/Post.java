package com.nfr.record.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonProperty(value = "post_name")
    @Column(unique = true)
    private String postName;

    @JsonProperty(value = "post_content")
    private String postContent;
}
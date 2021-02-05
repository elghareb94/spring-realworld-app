package io.spring.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@JsonRootName("article")
@NoArgsConstructor
public class ArticleDTO {

    private Long Id;

    @NotBlank(message = "can't be empty")
    private String title;

    @NotBlank(message = "can't be empty")
    private String description;

    @NotBlank(message = "can't be empty")
    private String body;

    private String[] tagList;
}

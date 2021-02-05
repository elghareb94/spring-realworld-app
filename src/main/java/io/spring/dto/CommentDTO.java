package io.spring.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@JsonRootName("comment")
public class CommentDTO {

    private Long id;

    @NotBlank(message = "can't be empty")
    private String body;
}
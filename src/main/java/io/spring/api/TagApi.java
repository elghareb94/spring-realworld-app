package io.spring.api;

import io.spring.entity.Tag;
import io.spring.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "tags")
public class TagApi {

    @Autowired
    private TagService tagService;


    @GetMapping
    public List<Tag> getAll() {
        return tagService.findAll();
    }
}

package io.spring.api;

import io.spring.config.AppConfig;
import io.spring.config.DataSourceConfig;
import io.spring.entity.Article;
import io.spring.service.ArticleService;
import io.spring.service.impl.ArticleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, AppConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
public class ArticleApiTest {

    private MockMvc mockMvc;

    private ArticleService articleServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        articleServiceMock = new ArticleServiceImpl();
        mockMvc = MockMvcBuilders.standaloneSetup(articleServiceMock).build();
    }


    @Test
    public void findAll_TodosFound_ShouldReturnFoundTodoEntries() throws Exception {
        Article article1 = Article.builder()
                .id(1L)
                .description("Lorem ipsum")
                .title("Foo")
                .build();

        when(articleServiceMock.findById(1L)).thenReturn(article1);
        mockMvc.perform(get("/articles/1")).andExpect(status().isOk());
    }

}

package io.spring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "slug")
    private final String slug;

    @Column(name = "title")
    private final String title;

    @Column(name = "description")
    private final String description;

    @Column(name = "body")
    private final String body;

    @CreationTimestamp
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private final ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss Z")
    private final ZonedDateTime updatedAt;
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Builder.Default
    @Getter
    private final Set<Comment> comments = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Builder.Default
    @JoinColumn(name = "article_id")
    private final Set<Tag> tags = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_articles_favorites",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    @JsonIgnore
    private final Set<User> favoriteUsers = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Setter
    private User author;

    public void addComment(Comment comment) {
        comment.setArticle(this);
        this.comments.add(comment);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addFavoriteUser(User user) {
        user.addFavoriteArticle(this);
        this.getFavoriteUsers().add(user);
    }

}

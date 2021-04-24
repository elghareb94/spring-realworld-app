package io.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "username", unique = true, nullable = false)
    private final String username;
    @Column(name = "email", unique = true, nullable = false)
    private final String email;
    @Column(name = "bio")
    private final String bio;
    @Column(name = "image_url")
    private final String imageUrl;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private final Set<Article> articles = new HashSet<>();
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private final Set<Comment> comments = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_follows",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id")})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private final Set<User> followings = new HashSet<>();
    @ManyToMany(mappedBy = "followings", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private final Set<User> followers = new HashSet<>();
    @ManyToMany(mappedBy = "favoriteUsers", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private final Set<Article> favoriteArticles = new HashSet<>();
    @Setter
    @Column(name = "password", nullable = false)
    private String password;


//    @OneToMany
//    private Set<Role> roles;

    public void addArticle(Article article) {
        article.setAuthor(this);
        this.articles.add(article);
    }

    public void addComment(Comment comment) {
        comment.setAuthor(this);
        this.comments.add(comment);
    }

    public void addFavoriteArticle(Article article) {
        article.getFavoriteUsers().add(this);
        this.favoriteArticles.add(article);
    }
}
//
//@Entity
//class Role{
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @OneToMany
//    Set<Permission> permissions;
//}
//
//@Entity
//class Permission{
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//}

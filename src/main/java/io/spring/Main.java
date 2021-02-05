package io.spring;


import io.spring.entity.Article;
import io.spring.entity.Comment;
import io.spring.entity.Tag;
import io.spring.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class Main {
    public static void main(String[] args) {

        double a = 25.1234;

        System.out.printf("'%.2f'%n",a);

//        SessionFactory factory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(Article.class)
//                .addAnnotatedClass(Comment.class)
//                .addAnnotatedClass(Tag.class)
//                .buildSessionFactory();
//
//        Session session = factory.getCurrentSession();
//        session.beginTransaction();
//
//
////        Article article = session.get(Article.class,1);
//
//        User user = User.builder()
//                .username("username")
//                .email("email@mail.com")
//                .password("Test1234")
//                .build();
//        Article article1 = Article.builder().body("body1").title("title1").build();
//        Article article2 = Article.builder().body("body2").title("title2").build();
////
//        user.getFavoriteArticles().add(article1);
//        user.getFavoriteArticles().add(article2);
//
////        session.save(article1);
////        session.save(article2);
//
//        session.save(user);
//
//        System.out.println(user);
//        session.getTransaction().commit();
//        session.close();

    }
}

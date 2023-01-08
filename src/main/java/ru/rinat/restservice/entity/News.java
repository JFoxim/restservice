package ru.rinat.restservice.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Type(type = "pg-uuid")
    @Column(name = "ID", length = 36)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User userCreator;

    @Column(name = "subject")
    private String subject;

    @Column(name="text", nullable = false)
    private String text;

    @Column(name="dt_create")
    private LocalDateTime createDate;

    public News(){}

    public News(User userCreator, String subject, String text, LocalDateTime createDate) {
        this.userCreator = userCreator;
        this.subject = subject;
        this.text = text;
        this.createDate = createDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", userCreatorId=" + userCreator.getId() +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id)
                && Objects.equals(createDate, news.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate);
    }
}

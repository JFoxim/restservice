package ru.rinat.restservice.dto;

import ru.rinat.restservice.entity.User;
import ru.rinat.restservice.model.dto.UserDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


public class NewsDto implements Serializable {

    private static final long serialVersionUID = -6721354416216073443L;
    private UUID id;

    private UserDto userCreator;

    private String subject;

    private String text;

    private LocalDateTime createDate;

    public NewsDto(){}

    public NewsDto(UUID id, UserDto userCreator, String subject, String text, LocalDateTime createDate) {
        this.id = id;
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

    public UserDto getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserDto userCreator) {
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
        return "NewsDto{" +
                "id=" + id +
                ", userCreator=" + userCreator +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}

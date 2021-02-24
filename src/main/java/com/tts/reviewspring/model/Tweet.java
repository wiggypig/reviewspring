package com.tts.reviewspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweet_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotEmpty(message = "Tweet cannot be empty")
    @Length(max = 280, message = "Tweet cannot have more than 280 characters")
    private String message;

    @CreationTimestamp
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "tweet_tag", joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    // Use the code below if your lombok is not working:
    // public Long getId() {
    // return id;
    // }

    // public User getUser() {
    // return user;
    // }

    // public void setUser(User user) {
    // this.user = user;
    // }

    // public List<Tag> getTags() {
    // return tags;
    // }

    // public void setTags(List<Tag> tags) {
    // this.tags = tags;
    // }

    // public String getMessage() {
    // return message;
    // }

    // public void setMessage(String message) {
    // this.message = message;
    // }

    // public Date getCreatedAt() {
    // return createdAt;
    // }

    // public void setCreatedAt(Date createdAt) {
    // this.createdAt = createdAt;
    // }

    // @Override
    // public String toString() {
    // return "Tweet [createdAt=" + createdAt + ", id=" + id + ", message=" +
    // message + ", tags=" + tags
    // + ", user=" + user + "]";
    // }


}

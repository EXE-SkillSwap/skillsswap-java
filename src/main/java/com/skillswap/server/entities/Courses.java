package com.skillswap.server.entities;

import com.skillswap.server.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseName;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String link;
    private double price;
    private double rating = 0.0;
    private String bannerUrl;
    @Column(columnDefinition = "LONGTEXT")
    private String achievements;
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

package com.example.techschool.dao;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.ZonedDateTime;


@Entity
@Table(name="check_in_outs")
@Data
@NoArgsConstructor
public class CheckInOut {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private LocalDate date;
    private ZonedDateTime checkIn;
    private ZonedDateTime checkOut;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}

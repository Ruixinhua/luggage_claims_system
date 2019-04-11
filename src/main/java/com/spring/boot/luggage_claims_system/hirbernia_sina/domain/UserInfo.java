package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserInfo {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "emailAddress should not null")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String emailAddress;

    @NotEmpty(message = "passport should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String passport;

    @NotEmpty(message = "password should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String password;

    @NotEmpty(message = "first name should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String firstName;

    @NotEmpty(message = "last name should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String lastName;

    @NotEmpty(message = "nickname should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @NotEmpty(message = "phoneNumber should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column
    private int role;

    @Column(nullable = false)
    private Date registerDate;

    @Column
    private Date loginDate;

    public UserInfo(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.emailAddress = userInfo.getEmailAddress();
        this.password = userInfo.getPassword();
        this.nickname = userInfo.getNickname();
    }
}

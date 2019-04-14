package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-03-26 21:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty(message = "emailAddress should not null")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String emailAddress;

    @NotEmpty(message = "passport should not null")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String passport;

    @NotEmpty(message = "password should not null")
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
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

    //    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Transient
    private Set<Role> roles = new HashSet<>();

    @Column
    private boolean enabled;

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

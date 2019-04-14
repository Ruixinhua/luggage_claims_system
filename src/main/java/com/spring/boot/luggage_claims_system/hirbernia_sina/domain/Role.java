package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-04-11 11:10
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = -2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String role;

    @Column
    private String describes;

//    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    private Set<Permission> permissions = new HashSet<>();

//    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    private Set<UserInfo> users = new HashSet<>();

    protected Role() {

    }
}

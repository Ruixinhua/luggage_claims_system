package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Liu Dairui
 * @date 2019-04-11 11:10
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    private int id;

    @Column
    private String role;

    @Column
    private String describes;

    @ManyToMany
    private Set<Permission> permissions;

    protected Role() {

    }
}

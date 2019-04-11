package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * The access permission to resource
 *
 * @author Liu Dairui
 * @date 2019-04-11 11:11
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    private Long id;
    @Column
    private String permission;
    @Column
    private String url;
    @Column
    private String describes;
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    protected Permission() {

    }
}

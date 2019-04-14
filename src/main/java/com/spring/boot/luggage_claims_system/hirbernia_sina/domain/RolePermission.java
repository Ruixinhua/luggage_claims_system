package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Liu Dairui
 * @date 2019-04-14 12:57
 */
@Data
@AllArgsConstructor
@Entity
@IdClass(RolePermission.class)
@Table(name = "role_permissions")
public class RolePermission implements Serializable {
    private static final long serialVersionUID = -1158141803682305656L;
    @Id
    private int rolesId;

    @Id
    private int permissionsId;

    protected RolePermission() {

    }
}

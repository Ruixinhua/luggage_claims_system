package com.spring.boot.luggage_claims_system.hirbernia_sina.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Liu Dairui
 * @date 2019-05-04 01:02
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "image")
public class FileManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fileName;
    @Column
    private Long serialNo;

    protected FileManager() {

    }
}

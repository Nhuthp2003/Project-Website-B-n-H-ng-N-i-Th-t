package com.arphor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "RoleId")
public class RoleId{
    
    @Id
    @Column(name = "RoleId")
    private int roleId;

    @Column(name = "RoleName", nullable = false)
    private String roleName;
}
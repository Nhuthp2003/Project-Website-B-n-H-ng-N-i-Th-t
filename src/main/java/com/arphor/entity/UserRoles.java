package com.arphor.entity;



import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class UserRoles implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "Gender")
    private Boolean gender;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;
    
    @Column(name = "deleted", nullable = false)
  	private Integer deleted = 0; 

    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    private RoleId role;

    @Column(name = "Images")
    private String images;
    
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	List<Discount> discount;

}

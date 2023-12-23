package com.arphor.model;

import java.io.Serializable;

import com.arphor.entity.RoleId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolersDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean gender;
    private String phone;
    private String address;
    private RoleId roleId;
    private String images;
    private Boolean isEdit;

}

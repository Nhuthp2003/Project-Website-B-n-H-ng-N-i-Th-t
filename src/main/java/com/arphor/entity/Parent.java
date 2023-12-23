package com.arphor.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Parent")
public class Parent {

	@Id
	private Integer parentId;
	private String parentName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "parent")
	List<Category> category; 
	
}
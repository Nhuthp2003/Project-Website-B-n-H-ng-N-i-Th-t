package com.arphor.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Category")
public class Category{

	@Id
    @Column(name = "CategoryID")
    private Integer categoryID;

    @Column(name = "CategoryName")
    private String categoryName;

    @Column(name = "Image")
    private String image;
    
    private String description;
    
    private Integer quantity;
    
    @ManyToOne
	@JoinColumn(name = "parentId")
	Parent parent;

	@JsonIgnore
    @OneToMany(mappedBy = "category")
	List<Product> product;
}
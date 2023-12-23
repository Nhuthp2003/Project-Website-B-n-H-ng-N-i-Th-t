package com.arphor.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "ProductName", nullable = false)
    private String productName;

	@Column(name = "Description")
	private String description;

    @Column(name = "Price", nullable = false)
    private double price;
    
    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    @Column(name = "Quantity")
    private Integer stock;

    @Column(name = "Images")
    private String image;

    @Column(name = "CreateDate")
    private LocalDateTime createDate;
    
    public String getName() {
		return productName;
	}
    
    @Column(name = "deleted", nullable = false)
	private Integer deleted = 0; 

	@ManyToOne
	@JoinColumn(name = "StatusProductId")
	private StatusProduct statusProduct;
	
	
	
	
	
}

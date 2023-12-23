package com.arphor.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Discount")
public class Discount{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountID")
    private Integer discountId;
    
    private String DiscountName;
    private String DiscountStatus;
    private String DiscountCode;
    private String DiscountProduct;

   
    @Column(name = "DiscountAmount", precision = 5, scale = 2, nullable = false)
    private double discountAmount;
    
	@ManyToOne
	@JoinColumn(name = "email")
	private UserRoles user;
    
    
}
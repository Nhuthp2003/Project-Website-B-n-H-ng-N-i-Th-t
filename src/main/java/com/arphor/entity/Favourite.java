package com.arphor.entity;

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
@Table(name = "Favourite")
public class Favourite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FavouriteID")
	private int favouriteID;

	@Column(name = "ProductID")
	private int productId;

	@Column(name = "Email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "ProductID", referencedColumnName = "ProductID", insertable = false, updatable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "Email", referencedColumnName = "Email", insertable = false, updatable = false)
	private UserRoles user;

	// Default constructor, getters, and setters
	// ...
	public Favourite() {
		// Default constructor
	}

	// Constructor
	public Favourite(int favouriteID, int productID, String email) {
		this.favouriteID = favouriteID;
		this.productId = productID;
		this.email = email;
	}

}
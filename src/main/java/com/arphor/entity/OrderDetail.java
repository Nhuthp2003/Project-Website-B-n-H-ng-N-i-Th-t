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
@Table(name = "OrderDetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderDetailID")
	private Integer orderDetailId;

	@Column(name = "OrderID")
	private Integer orderId;

	@Column(name = "ProductID")
	private String productId;

	@Column(name = "Quantity", nullable = false)
	private Integer quantity;

	@Column(name = "Price", nullable = false)
	private Double price;

	@ManyToOne
	@JoinColumn(name = "orderId", referencedColumnName = "OrderID", insertable = false, updatable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "ProductID", referencedColumnName = "ProductID", insertable = false, updatable = false)
	private Product product;

}
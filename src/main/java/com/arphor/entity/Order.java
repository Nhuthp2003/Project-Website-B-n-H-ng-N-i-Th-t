package com.arphor.entity;

import java.util.Date;
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

import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderID")
	private Integer orderId;

	@Column(name = "Email")
	private String email;

	@Column(name = "OrderDate", nullable = false)
	private Date orderDate = new Date();

	@Column(name = "Address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "Email", referencedColumnName = "Email", insertable = false, updatable = false)
	private UserRoles userRoles;

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;
	
	@ManyToOne
	@JoinColumn(name = "StatusOrderID")
	private StatusOrder statusOrder;
}
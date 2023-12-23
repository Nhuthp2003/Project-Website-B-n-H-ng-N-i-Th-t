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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StatusOrder")
public class StatusOrder {
	@Id
	private Integer StatusOrderID;
	private String StatusName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "statusOrder")
	List<Order> order;
}
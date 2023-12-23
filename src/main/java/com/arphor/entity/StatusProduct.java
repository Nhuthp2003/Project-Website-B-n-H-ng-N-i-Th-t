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
@Table(name = "StatusProduct")
public class StatusProduct {
	
	@Id
	private Integer StatusProductId;
	private String statusName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "statusProduct")
	List<Product> product;
}
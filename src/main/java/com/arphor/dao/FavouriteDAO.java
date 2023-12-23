package com.arphor.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arphor.entity.Favourite;

public interface FavouriteDAO extends JpaRepository<Favourite, String>{

	List<Favourite> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("DELETE FROM Favourite f WHERE f.email = :email AND f.productId = :productId")
	void deleteByEmailAndProductId(@Param("email") String email, @Param("productId") int productId);

	@Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favourite f WHERE f.email = :email AND f.product.id = :productId")
    boolean productFavouritedYet(@Param("email") String email, @Param("productId") int productId);
    
}

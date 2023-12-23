package com.arphor.service;

import java.util.List;

import com.arphor.entity.Favourite;

public interface FavouriteService {

	Favourite findFavouriteById(String favouriteId);

    List<Favourite> getAllFavourites();

    List<Favourite> getFavouritesByEmail(String email);

    Favourite saveFavourite(Favourite favourite);

    void deleteFavourite(String favouriteId);
    
    void addToFavourites(String email, Favourite favourite);

	void deleteFromFavourites(String email, int product);

	boolean isProductFavourited(String email, int product);
}

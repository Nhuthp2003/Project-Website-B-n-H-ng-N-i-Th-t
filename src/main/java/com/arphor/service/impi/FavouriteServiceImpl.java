package com.arphor.service.impi;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arphor.dao.FavouriteDAO;
import com.arphor.entity.Favourite;
import com.arphor.service.FavouriteService;

@Service
public class FavouriteServiceImpl implements FavouriteService {

	private final FavouriteDAO favouriteDAO;

	public FavouriteServiceImpl(FavouriteDAO favouriteDAO) {
		this.favouriteDAO = favouriteDAO;
	}

	@Override
	public Favourite findFavouriteById(String favouriteId) {
		return favouriteDAO.findById(favouriteId).orElse(null);
	}

	@Override
	public List<Favourite> getAllFavourites() {
		return favouriteDAO.findAll();
	}

	@Override
	public List<Favourite> getFavouritesByEmail(String email) {
		return favouriteDAO.findByEmail(email);
	}

	@Override
	public Favourite saveFavourite(Favourite favourite) {
		return favouriteDAO.save(favourite);
	}

	@Override
	public void deleteFavourite(String favouriteId) {
		favouriteDAO.deleteById(favouriteId);
	}

	@Override
	public void addToFavourites(String email, Favourite favourite) {
		favouriteDAO.save(favourite);
	}

	@Override
	public void deleteFromFavourites(String email, int product) {
		favouriteDAO.deleteByEmailAndProductId(email, product);
	}

	@Override
	public boolean isProductFavourited(String email, int product) {
		// TODO Auto-generated method stub
		boolean isFavourites = favouriteDAO.productFavouritedYet(email, product);

		return isFavourites;
	}
}
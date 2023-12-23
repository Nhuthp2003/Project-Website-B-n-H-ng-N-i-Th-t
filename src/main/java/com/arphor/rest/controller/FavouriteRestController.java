package com.arphor.rest.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arphor.entity.Favourite;
import com.arphor.service.FavouriteService;

@RestController
@RequestMapping("/rest/favourites")
public class FavouriteRestController {

    private final FavouriteService favouriteService;

    public FavouriteRestController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping("/{favouriteId}")
    public Favourite findFavouriteById(@PathVariable String favouriteId) {
        return favouriteService.findFavouriteById(favouriteId);
    }

    @GetMapping
    public List<Favourite> getAllFavourites() {
        return favouriteService.getAllFavourites();
    }

    @GetMapping("/email/{email}")
    public List<Favourite> getFavouritesByEmail(@PathVariable String email) {
        return favouriteService.getFavouritesByEmail(email);
    }

    @PostMapping
    public Favourite saveFavourite(@RequestBody Favourite favourite) {
        return favouriteService.saveFavourite(favourite);
    }

    @DeleteMapping("/{favouriteId}")
    public void deleteFavourite(@PathVariable String favouriteId) {
        favouriteService.deleteFavourite(favouriteId);
    }
}

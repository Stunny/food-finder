package edu.salleurl.ls30394.foodfinderapp.repositories;

/**
 * Created by avoge on 19/05/2017.
 */

public interface RecentSearchesRepo {

    void addRecentSearch(int userId, String searchQuery);

    String[] getRecentSearches();

    void removeRecentSearch(String searchQuery);

}

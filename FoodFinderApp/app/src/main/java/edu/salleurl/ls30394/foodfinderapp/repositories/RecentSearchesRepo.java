package edu.salleurl.ls30394.foodfinderapp.repositories;

import java.util.List;

/**
 * Created by avoge on 19/05/2017.
 */

public interface RecentSearchesRepo {

    void addRecentSearch(int userId, String searchQuery);

    List<String> getRecentSearches(int userId);

    void removeRecentSearch(int userId, String searchQuery);

    boolean exists(int userId, String searchQuery);

}

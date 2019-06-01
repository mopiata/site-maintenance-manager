package dao;
import models.Site;
import java.util.List;

public interface SiteDao {
    //CREATE
    void save(Site site);

    //READ AND LIST
    List<Site> all();
    Site findById(int id);

    //UPDATE
    void update(int id, String name, String location, int engineerId);

    //DELETE
    void deleteById(int id);
    void clearAllSites();
}

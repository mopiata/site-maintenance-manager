package dao;
import models.Engineer;
import models.Site;
import org.sql2o.*;
import org.junit.*;
import org.junit.Rule;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oSiteDaoTest {
    private static Sql2oSiteDao siteDao=new Sql2oSiteDao(DB.sql2o);
    private static Sql2oEngineerDao engineerDao=new Sql2oEngineerDao(DB.sql2o);

    //helper methods
    public Site addSite(){
        return new Site("Ritongo", "Western", 1);
    }

    public Site addOtherSite(){
        return new Site("QOA", "Kasarani", 2);
    }

    public Engineer addEngineer(){
        return new Engineer(5454,"Maurice Githaiga","0721534728");
    }

    public Engineer addOtherEngineer(){
        return new Engineer(5555,"Henrieta Zuri","0723000000");
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void NewSiteObjectCorrectlyCreated_true() throws Exception {
        Site site=addSite();
        assertEquals(true, site instanceof Site);
    }

    @Test
    public void saveSiteSetsId() throws Exception{
        Site site=new Site("Ritongo","Western", 1);
        int originalSiteId=site.getId();
        siteDao.save(site);
        assertNotEquals(originalSiteId,site.getId());
    }

    @Test
    public void saveSiteSavesEngineerId() throws Exception{
        Engineer engineer=addEngineer();
        engineerDao.save(engineer);
        Site site = new Site("Ritongo","Western",engineer.getId());
        siteDao.save(site);
        assertEquals(site.getEngineerId(),engineer.getId());
    }

    @Test
    public void existingSitessCanBeFoundById() throws Exception {
        Site site=addSite();
        siteDao.save(site); //add to dao (takes care of saving)

        Site foundSite=siteDao.findById(site.getId());
        assertEquals(site, foundSite); //should be the same
    }

    @Test
    public void all_returnsNothingIfNoSites() throws Exception  {
        List<Site> sites=siteDao.all();
        assertTrue(sites.size()==0);
    }

    @Test
    public void all_returnsAllSiteInstances() throws Exception  {
        Site site=addSite();
        Site otherSite=addOtherSite();

        siteDao.save(site);
        siteDao.save(otherSite);

        List<Site> sites=siteDao.all();

        assertTrue(sites.size()==2);
        assertTrue(sites.contains(site));
        assertTrue(sites.contains(otherSite));
    }

//    @Test
//    public void updateUpdatesEntryInDb_true() throws Exception{
//        Site site=addSite();
//        siteDao.save(site);
//        String formerName=site.getName();
//        int id=site.getId();
//        siteDao.update(id,"Gospel","Nairobi",1);
//        String newName=site.getName();
//
//        assertNotEquals(formerName,newName);
//    }

    @Test
    public void existingSitesCanBeDeletedById() throws Exception {
        Site site=addSite();
        siteDao.save(site); //add to dao (takes care of saving)
        siteDao.deleteById(site.getId());
        assertFalse(siteDao.all().contains(site)); //should be the same
    }

    @Test
    public void allExistingSitesCanBeDeleted() throws Exception {
        Site site=addSite();
        Site otherSite=addOtherSite();

        siteDao.save(site); //add to dao (takes care of saving)
        siteDao.save(otherSite);
        siteDao.clearAllSites();
        assertTrue(siteDao.all().size()==0); //should be the same
    }
}
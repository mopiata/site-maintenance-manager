package dao;

import models.Site;
import models.Site;
import org.sql2o.*;
import java.util.List;

public class Sql2oSiteDao implements SiteDao {

    private final Sql2o sql2o;

    public Sql2oSiteDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }

    @Override
    public void save(Site site){
        String sql="INSERT INTO sites (name,location,engineerid) VALUES (:name, :location, :engineerId);";

        try(Connection con=DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(site)
                    .executeUpdate()
                    .getKey();

            site.setId(id);
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Site> all(){
        try(Connection con=DB.sql2o.open()){

            return con.createQuery("SELECT * FROM sites;")
                    .executeAndFetch(Site.class);
        }
    }

    @Override
    public Site findById(int id){

        try(Connection con=DB.sql2o.open()){
            System.out.println(id);
            return con.createQuery("SELECT * FROM sites WHERE id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Site.class);
        }
    }

    @Override
    public void update(int id, String name, String location, int engineerId){
        String sql = "UPDATE sites SET name = :name, location=:location, engineerid=:engineerid WHERE id=:id";
        try(Connection con=DB.sql2o.open()){
            System.out.println(id);
            con.createQuery(sql)
                    .addParameter("id",id)
                    .addParameter("name",name)
                    .addParameter("location",location)
                    .addParameter("engineerid",engineerId)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteById(int id){
        try(Connection con=DB.sql2o.open()){
            con.createQuery("DELETE FROM sites WHERE id = :id")
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }

    @Override
    public void clearAllSites(){
        try(Connection con=DB.sql2o.open()){
            con.createQuery("DELETE FROM sites")
                    .executeUpdate();
        }
    }

}

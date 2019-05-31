package dao;

import models.Engineer;
import org.sql2o.*;
import java.util.List;

public class Sql2oEngineerDao implements EngineerDao {
    private final Sql2o sql2o;

    public Sql2oEngineerDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }

    @Override
    public void save(Engineer engineer){
        String sql="INSERT INTO engineers (name,eknumber,phone) VALUES (:name, :ekNumber, :phone);";

        try(Connection con=DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(engineer)
                    .executeUpdate()
                    .getKey();

            engineer.setId(id);
        }catch(Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Engineer> all(){
        try(Connection con=DB.sql2o.open()){

            return con.createQuery("SELECT * FROM engineers;")
                    .executeAndFetch(Engineer.class);
        }
    }

    @Override
    public Engineer findById(int id){

        try(Connection con=DB.sql2o.open()){
            System.out.println(id);
            return con.createQuery("SELECT * FROM engineers WHERE id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Engineer.class);
        }
    }

}

package dao;

import org.junit.rules.ExternalResource;
import org.sql2o.*;
import environments.Constants;

public class DatabaseRule extends ExternalResource {
    @Override
    protected void before() {
        DB.sql2o=new Sql2o(Constants.URL_TEST,Constants.USERNAME,Constants.PASSWORD);
    }

    @Override
    protected void after(){
        try(Connection con=DB.sql2o.open()){
            String deleteEngineersQuery="DELETE FROM engineers *;";
            con.createQuery(deleteEngineersQuery)
                    .executeUpdate();
        }
    }
}
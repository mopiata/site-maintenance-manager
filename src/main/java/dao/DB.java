package dao;
import environments.Constants;

import org.sql2o.*;

public class DB {
    public static Sql2o sql2o = new Sql2o(Constants.URL_PROD, Constants.USERNAME, Constants.PASSWORD);
}

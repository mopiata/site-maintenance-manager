package dao;
import models.Engineer;
import java.util.List;

public interface EngineerDao {
    //CREATE
    void save(Engineer engineer);

    //READ AND LIST
    List<Engineer> all();
    Engineer findById(int id);

    //UPDATE
    void update(int id, int ekNumber, String name, String phone);

    //DELETE
    void deleteById(int id);
    void clearAllEngineers();
}

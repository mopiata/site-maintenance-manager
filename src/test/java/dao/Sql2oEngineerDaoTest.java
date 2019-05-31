package dao;
import models.Engineer;
import org.sql2o.*;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oEngineerDaoTest {
    private static Sql2oEngineerDao engineerDao=new Sql2oEngineerDao(DB.sql2o);
    private Connection con;

    //helper mathods
    public Engineer addEngineer(){
        return new Engineer(5454,"Maurice Githaiga","0721534728");
    }

    public Engineer addOtherEngineer(){
        return new Engineer(5555,"Henrieta Zuri","0723000000");
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void saveEngineerSetsId() throws Exception{
        Engineer engineer=addEngineer();
        int originalEngineerId=engineer.getId();
        engineerDao.save(engineer);
        assertNotEquals(originalEngineerId,engineer.getId());
    }

    @Test
    public void existingEngineersCanBeFoundById() throws Exception {
        Engineer engineer=addEngineer();
        engineerDao.save(engineer); //add to dao (takes care of saving)

        Engineer foundEngineer=engineerDao.findById(engineer.getId());
        assertEquals(engineer, foundEngineer); //should be the same
    }

    @Test
    public void all_returnsNothingIfNoEngineers() throws Exception  {
        List<Engineer> engineers=engineerDao.all();
        assertTrue(engineers.size()==0);
    }

    @Test
    public void all_returnsAllEngineerInstances() throws Exception  {
        Engineer engineer=addEngineer();
        Engineer otherEngineer=addOtherEngineer();

        engineerDao.save(engineer);
        engineerDao.save(otherEngineer);

        List<Engineer> engineers=engineerDao.all();

        assertTrue(engineers.size()==2);
        assertTrue(engineers.contains(engineer));
        assertTrue(engineers.contains(otherEngineer));
    }
}

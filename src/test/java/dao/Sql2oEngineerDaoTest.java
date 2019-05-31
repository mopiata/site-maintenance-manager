package dao;
import models.Engineer;
import org.sql2o.*;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oEngineerDaoTest {
    private static Sql2oEngineerDao engineerDao=new Sql2oEngineerDao(DB.sql2o);

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
    public void NewEngineerObjectCorrectlyCreated_true() throws Exception {
        Engineer engineer=addEngineer();
        assertEquals(true, engineer instanceof Engineer);
    }

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

//    @Test
//    public void updateUpdatesEntryInDb_true() throws Exception{
//        Engineer engineer=addEngineer();
//        engineerDao.save(engineer);
//        int id=engineer.getId();
//        engineerDao.update(id,6789,"Ricky","56788292");
//        String newName=engineer.getName();
//
//        assertEquals("Ricky",newName);
//    }

    @Test
    public void existingEngineersCanBeDeletedById() throws Exception {
        Engineer engineer=addEngineer();
        engineerDao.save(engineer); //add to dao (takes care of saving)
        engineerDao.deleteById(engineer.getId());
        assertFalse(engineerDao.all().contains(engineer)); //should be the same
    }

    @Test
    public void allExistingEngineersCanBeDeleted() throws Exception {
        Engineer engineer=addEngineer();
        Engineer otherEngineer=addOtherEngineer();

        engineerDao.save(engineer); //add to dao (takes care of saving)
        engineerDao.save(otherEngineer);
        engineerDao.clearAllEngineers();
        assertTrue(engineerDao.all().size()==0); //should be the same
    }


}

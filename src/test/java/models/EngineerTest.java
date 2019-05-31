package models;

import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import dao.DatabaseRule;

public class EngineerTest {

    //helper mathods
    public Engineer addEngineer(){
        return new Engineer(5454,"Maurice Githaiga","0721534728");
    }

    public Engineer addOtherEngineer(){
        return new Engineer(5555,"Henrieta Zuri","0723000000");
    }

    //tests
    @Test
    public void NewEngineerObjectCorrectlyCreated_true() throws Exception {
        Engineer engineer=addEngineer();
        assertEquals(true, engineer instanceof Engineer);
    }

}
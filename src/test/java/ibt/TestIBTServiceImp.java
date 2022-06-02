/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibt;

import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.ibt.repository.IBTRepositoryImp;
import co.za.carolsBoutique.ibt.service.IBTIdGenerator;
import co.za.carolsBoutique.ibt.service.IBTServiceImp;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class TestIBTServiceImp {
    
        IBTRepositoryImp dao;
	IBTIdGenerator gen;
	IBTServiceImp iBTService = null;
    
    public TestIBTServiceImp() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        dao = new IBTRepositoryImp();
        gen = new IBTIdGenerator();
        iBTService = new IBTServiceImp(dao,gen);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFindStoreIBT(){
        List<IBT> ibtTest = dao.findStoreIBTS("");
        assertEquals(ibtTest, iBTService.getIBT(""));
    }
    
    @Test
    public void testRequestIBT(){
       
    }
    
    //
    // @Test
    // public void hello() {}
}

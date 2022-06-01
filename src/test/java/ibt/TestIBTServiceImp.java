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
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testRequestIBT(){
       //  testIbt = new IBT("Ib12","fred@vzap.co.za", "joburg", 200.23, "123456789012");
	//	Assert.assertEquals("Boutique added, boutique Id=" + testIbt.getId(), iBTService.testRequest(testIbt);
    }
    
    //
    // @Test
    // public void hello() {}
}

package se.anosh.jbstag;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import se.anosh.jbstag.dao.GbsFileReader;
import se.anosh.jbstag.domain.GbsTag;

@Deprecated
public class TestGbsReading {

	    public TestGbsReading() {
			super();
		}

		private GbsFileReader gbsFile;
	    private GbsTag tags;
	    
	    
	    @Before
	    public void setup() throws IOException {
	        
	        gbsFile = new GbsFileReader("gbs/sample.gbs");
	        tags = gbsFile.getTags();
	    }
		
	    @Test
	    public void TestOpenFile() {
	    	
	    	System.out.println(tags.toString());
	    	System.out.println(tags.equals(tags));
	    	System.out.println(tags.hashCode());
	    	
	    }
	    

}

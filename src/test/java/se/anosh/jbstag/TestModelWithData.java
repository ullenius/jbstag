package se.anosh.jbstag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.anosh.jbstag.dao.GbsFileReader;
import se.anosh.jbstag.domain.GbsTag;

public class TestModelWithData {
	
    public TestModelWithData() {
		super();
	}

	private GbsFileReader reader;
    private GbsTag tag;
    private static String FIRST_SAMPLE_FILE = "gbs/sample.gbs"; // Shantae
    private static String SECOND_SAMPLE_FILE = "gbs/sample2.gbs"; // DK Land2
    
    @Before
    public void setup() throws IOException {
        
        reader = new GbsFileReader(FIRST_SAMPLE_FILE);
        tag = reader.getTags();
    }
	
    @Test
    public void testIdenticalHashCodes() throws IOException {
        GbsFileReader cloneFile = new GbsFileReader(FIRST_SAMPLE_FILE);
        GbsTag clone = cloneFile.getTags();
        
        System.out.println("tag = " + tag);
        System.out.println("clone = " + clone);
        
        
        assertNotSame(clone,tag); // don't cheat
        assertEquals(tag.hashCode(), clone.hashCode());
    }
    
    @Test
    public void testNotIdenticalHashCodes() throws IOException {
        
        GbsFileReader different = new GbsFileReader(SECOND_SAMPLE_FILE);
        GbsTag differentGbsTag = different.getTags();
        
        assertNotSame(differentGbsTag,tag); // object references
        assertNotEquals(differentGbsTag,tag); // while we're at it
        assertNotEquals(differentGbsTag.hashCode(),tag.hashCode());
    }
    
    @Test
    public void testEqualObjects() throws IOException {
        
    	GbsFileReader cloneFile = new GbsFileReader(FIRST_SAMPLE_FILE);
        GbsTag clone = cloneFile.getTags();
        
        assertNotSame(clone,tag); // no cheating
        assertEquals(clone.hashCode(),tag.hashCode()); // equal objects *MUST* have equals hashcodes
        assertEquals(clone,tag);
    }
    
    @Test
    public void testNonEqualObjects() throws IOException {
        
    	GbsFileReader other = new GbsFileReader(SECOND_SAMPLE_FILE);
         GbsTag dkland = other.getTags();
         
         assertNotEquals(dkland,tag);
    }
    
    @Test
    public void testComparableSorting() throws IOException {
        
    	GbsFileReader otherFile = new GbsFileReader(SECOND_SAMPLE_FILE);
        GbsTag other = otherFile.getTags();
        
        List<GbsTag> myList = new ArrayList<>();
        myList.add(other);
        myList.add(tag);
        myList.add(other);
        myList.add(tag);
        
        myList.sort(null);
        myList.forEach(System.out::println);
        assertEquals(tag,myList.get(3));
        assertEquals(tag,myList.get(2));
        assertEquals(other,myList.get(1));
        assertEquals(other,myList.get(0));
        
    }
    
    @Test
    public void testComparableWithNullValues() throws IOException {
        
    	GbsFileReader otherFile = new GbsFileReader(SECOND_SAMPLE_FILE);
        GbsTag other = otherFile.getTags();
        
        other.setTitle(null);
        other.setAuthor(null);
        other.setCopyright(null);
        tag.setTitle(null);
        
        List<GbsTag> myList = new ArrayList<>();
        myList.add(tag);
        myList.add(other);
        
        myList.sort(null);
        
        assertNull(myList.get(0).getTitle());
        assertNull(myList.get(0).getAuthor());
        assertNull(myList.get(0).getCopyright());
        assertNull(myList.get(1).getTitle());
        assertNotNull(myList.get(1).getAuthor());
        assertNotNull(myList.get(1).getCopyright());
    }
    
    @Test
    public void testFileWithValidHeader() {
        
        // first 3 bytes of string should equal "GBS"
        final String headerWithoutVersionNumber = tag.getHeader();
        assertEquals("GBS", headerWithoutVersionNumber); // case sensitive
    }
    
    @Test(expected=IOException.class)
    public void testFileWithInvalidHeader() throws IOException {
        // tests a file that is not a GBS
        reader = new GbsFileReader("gbs/randomBytes.gbs"); // will throw exception
        
    }
    
	

}

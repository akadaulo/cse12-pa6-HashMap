import static org.junit.Assert.*;

import org.junit.*;

public class FileDataTest {


    @Test
    public void testConstructor() {
        String name = "test.txt";
        String dir = "/test/directory";
        String date = "2024-05-29";

        FileData fileData = new FileData(name, dir, date);

        assertEquals("Name should be 'test.txt'", name, fileData.name);
        assertEquals("Directory should be '/test/directory'", dir, fileData.dir);
        assertEquals("Last modified date should be '2024-05-29'", date, fileData.lastModifiedDate);
    }




    @Test
    public void testString() {
        String name = "test.txt";
        String dir = "/test/directory";
        String date = "2024-05-29";

        FileData fileData = new FileData(name, dir, date);

        String expectedString = "FileData{name='test.txt', dir='/test/directory', lastModifiedDate='2024-05-29'}";
        assertEquals("toString method should return the correct string representation", expectedString, fileData.toString());
    }




}

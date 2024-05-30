import static org.junit.Assert.*;

import org.junit.*;

public class FileSystemTest {

    FileSystem fileSystem;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }

    public void testAdd() {
        assertTrue(fileSystem.add("test.txt", "/test/directory", "2024-05-29"));
        FileData fileData = fileSystem.findFile("test.txt", "/test/directory");
        assertNotNull(fileData);
        assertEquals("test.txt", fileData.name);
        assertEquals("/test/directory", fileData.dir);
        assertEquals("2024-05-29", fileData.lastModifiedDate);
    }

    @Test
    public void testRemoveFile() {
        fileSystem.add("test.txt", "/test/directory", "2024-05-29");
        assertTrue(fileSystem.removeFile("test.txt", "/test/directory"));
        assertNull(fileSystem.findFile("test.txt", "/test/directory"));
    }


}

package mouse.hoi.lib.filemanager;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerImplTest {

    @Test
    void readWrite() {
        String testFile = "src/test/resources/files/sample.txt";
        FileManager fileManager = new FileManagerImpl();
        fileManager.clear(testFile);
        String r1 = fileManager.read(testFile);
        assertTrue(r1.isEmpty());

        fileManager.write(testFile, "Hello");
        String r2 = fileManager.read(testFile);
        assertEquals("Hello", r2);

        fileManager.write(testFile, "Hello2");
        String r3 = fileManager.read(testFile);
        assertEquals("Hello2", r3);

        fileManager.append(testFile, "\nHello3");
        String r4 = fileManager.read(testFile);
        assertEquals("Hello2\nHello3", r4);

        List<String> r5 = fileManager.readLines(testFile);
        assertArrayEquals(new String[]{"Hello2", "Hello3"}, r5.toArray(new String[0]));
    }
}
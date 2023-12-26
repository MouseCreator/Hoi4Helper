package mouse.hoi.parser.parse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileFormatterTest {
    private FileFormatter fileFormatter;

    @BeforeEach
    void setUp(){
         fileFormatter = new FileFormatter();
    }
    @Test
    void formatFileContent_Regular() {

        String content= """
                A  = {
                  b = {
                   c
                  }
                }
                """;

        String s = fileFormatter.formatFileContent(content);
        assertEquals("A = { b = { c } }", s);
    }

    @Test
    void formatFileContent_ExtraSpaces() {

        String content= """
                A  = {
                
                
                d = {   a = 0 }
                  b = {
                   c
                   
                  }
                }
                    if = {
                    }
                }
                """;

        String s = fileFormatter.formatFileContent(content);
        System.out.println(s);
    }
}
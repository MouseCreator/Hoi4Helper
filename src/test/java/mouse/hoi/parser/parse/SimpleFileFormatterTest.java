package mouse.hoi.parser.parse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFileFormatterTest {
    private SimpleFileFormatter simpleFileFormatter;

    @BeforeEach
    void setUp(){
         simpleFileFormatter = new SimpleFileFormatter();
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

        String s = simpleFileFormatter.formatFileContent(content);
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

        String s = simpleFileFormatter.formatFileContent(content);
        System.out.println(s);
    }
}
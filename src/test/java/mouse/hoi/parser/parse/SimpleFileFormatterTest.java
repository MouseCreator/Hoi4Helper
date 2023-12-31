package mouse.hoi.parser.parse;

import mouse.hoi.gamefiles.parser.parse.SimpleFileFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        assertEquals("A = { d = { a = 0 } b = { c } } if = { } }", s);
    }

    @Test
    void tokenizeTest() {

        String content= """
                A = "hello world"
                b   = { c   = "a" }
                """;

        List<String> s = simpleFileFormatter.formatAndTokenize(content);
        assertEquals("[A, =, \"hello world\", b, =, {, c, =, \"a\", }]", s.toString());
    }

    @Test
    void noCommentsTest() {

        String content= """
                # Comment
                # Comment
                Actual #Not a comment
                #Comment
                    #Comment#Double#
                """;

        String s = simpleFileFormatter.formatFileContent(content);
        assertEquals("Actual", s);
    }
}
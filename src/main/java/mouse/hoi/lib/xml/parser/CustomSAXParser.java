package mouse.hoi.lib.xml.parser;

import mouse.hoi.lib.xml.fill.FillableCreator;
import mouse.hoi.lib.xml.fill.Filler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
@Service
public class CustomSAXParser implements Parser {

    private SAXParserFactory SAXFactory;
    private Filler filler;
    private FillableCreator fillableCreator;
    public CustomSAXParser() {}

    @Autowired
    public CustomSAXParser(SAXParserFactory saxFactory, Filler filler, FillableCreator fillableCreator) {
        SAXFactory = saxFactory;
        this.filler = filler;
        this.fillableCreator = fillableCreator;
    }


    @Override
    public Object parse(String filename) {
        try {
            SAXParser saxParser = SAXFactory.newSAXParser();
            SAXPaperHandler saxPaperHandler = new SAXPaperHandler();
            saxPaperHandler.setCreator(fillableCreator);
            saxPaperHandler.setFiller(filler);
            saxParser.parse(filename, saxPaperHandler);
            return saxPaperHandler.getResult();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}

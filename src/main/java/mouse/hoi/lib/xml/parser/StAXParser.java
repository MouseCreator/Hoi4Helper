package mouse.hoi.lib.xml.parser;


import mouse.hoi.lib.xml.fill.FillableCreator;
import mouse.hoi.lib.xml.fill.Filler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
@Service
public class StAXParser implements Parser {
    private final FillableCreator creator;
    private final Filler filler;
    @Autowired
    public StAXParser(FillableCreator creator, Filler filler) {
        this.creator = creator;
        this.filler = filler;
    }

    @Override
    public Object parse(String filename) {

        try {
            InputStream inputStream = new FileInputStream(filename);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            return parseElements(reader);
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseElements(XMLStreamReader reader) throws XMLStreamException {
        return parseElements(null, reader, "");
    }

    private Object parseElements(Object result, XMLStreamReader reader, String element) throws XMLStreamException {
        String currentElement = element;
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT -> {
                    currentElement = reader.getLocalName();
                    if (creator.isElementDeclaration(currentElement)) {
                        if (result == null) {
                            result = initializeWithAttributes(reader, currentElement);
                        } else {
                            Object childNode = initializeWithAttributes(reader, currentElement);
                            Object toFill = parseElements(childNode, reader, element);
                            filler.fill(result, currentElement, toFill);
                        }
                    }
                }
                case XMLStreamConstants.CHARACTERS -> {
                    String text = reader.getText().trim();
                    if (text.isEmpty())
                        continue;
                    filler.fill(result, currentElement, text);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    currentElement = reader.getLocalName();
                    if (creator.isElementDeclaration(currentElement)) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private Object initializeWithAttributes(XMLStreamReader reader, String currentElement) {
        Object result = creator.createNew(currentElement);
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            String name = reader.getAttributeName(i).toString();
            String value = reader.getAttributeValue(i);
            filler.fill(result, name, value);
        }
        return result;
    }
}

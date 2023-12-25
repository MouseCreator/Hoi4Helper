package mouse.hoi.lib.xml.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import mouse.hoi.lib.xml.fill.FillableCreator;
import mouse.hoi.lib.xml.fill.Filler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
@Service
@Primary
public class DOMParser implements Parser {
    private FillableCreator creator;
    private Filler filler;

    public DOMParser() {}
    @Autowired
    public DOMParser(FillableCreator creator, Filler filler) {
        this.creator = creator;
        this.filler = filler;
    }

    @Override
    public Object parse(String filename) {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            document.getDocumentElement().normalize();

            return processNode(document.getDocumentElement());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object processNode(Node node) {
        Object result = creator.createNew(node.getNodeName());
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            filler.fill(result, attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node detailNode = childNodes.item(i);
            if (creator.isElementDeclaration(detailNode.getNodeName())) {
                Object child = processNode(detailNode);
                filler.fill(result, detailNode.getNodeName(), child);
            } else {
                String textContent = detailNode.getTextContent().trim();
                if (!textContent.isEmpty())
                    filler.fill(result, detailNode.getNodeName(), textContent);
            }
        }
        return result;
    }

}

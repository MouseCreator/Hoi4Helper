package mouse.hoi.parser.parse;

import mouse.hoi.exception.GameFileParseException;
import mouse.hoi.parser.property.input.Property;
import mouse.hoi.parser.propertyfactory.PropertyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyParser {

    private final PropertyFactory propertyFactory;
    @Autowired
    public PropertyParser(PropertyFactory propertyFactory) {
        this.propertyFactory = propertyFactory;
    }

    public List<Property> parse(TokenCollection tokenCollection) {
        return createProperty(tokenCollection);
    }

    private List<Property> createProperty(TokenCollection collection) {
        List<Property> createdProperties = new ArrayList<>();
        if (collection.isEmpty()) {
            return createdProperties;
        }
        while (collection.hasNext()) {
            String key = collection.nextToken();
            if (!isRegularToken(key)) {
                throw new GameFileParseException("Regular key is expected, but got " + key);
            }
            if (collection.isEmpty()) {
                createdProperties.add(propertyFactory.simple().get(key));
                break;
            }
            String next = collection.look();
            if (isRegularToken(next)) {
                createdProperties.add(propertyFactory.simple().get(key));
                continue;
            }

            if (!isEqualSign(next)) {
                throw new GameFileParseException("Unexpected combination of tokens: " + key + ", " + next);
            }
            collection.skip();

            if (collection.isEmpty()) {
                throw new GameFileParseException("Unexpected end of collection: " + key + " = NO_VALUE");
            }

            String value = collection.nextToken();
            if (opensBlock(value)) {
                createBlock(collection, createdProperties, key, "");
                continue;
            }
            if (collection.isEmpty()) {
                createdProperties.add(propertyFactory.fieldValue().get(key, value));
                break;
            }
            if (opensBlock(collection.look())) {
                createBlock(collection, createdProperties, key, value);
            } else {
                createdProperties.add(propertyFactory.fieldValue().get(key,value));
            }
        }

        return createdProperties;


    }

    private void createBlock(TokenCollection collection, List<Property> createdProperties, String key, String value) {
        TokenCollection block = getBlock(collection);
        List<Property> properties = createProperty(block);
        createdProperties.add(propertyFactory.block().with(key, value).with(properties).get());
    }

    private TokenCollection getBlock(TokenCollection collection) {
        int bracketCount = 1;
        if (collection.isEmpty()) {
            return new TokenCollection();
        }
        if (opensBlock(collection.look())) {
            collection.skip();
        }
        TokenCollection tokenCollection = new TokenCollection();
        while (collection.hasNext()) {
            String token = collection.nextToken();
            if (opensBlock(token)) {
                bracketCount++;
            }
            if (closesBlock(token)) {
                bracketCount--;
            }
            if (bracketCount == 0) {
                return tokenCollection;
            }
            tokenCollection.addToken(token);

        }
        throw new GameFileParseException("Unable to find closing bracket. Miscount: " + bracketCount);
    }

    private boolean isEqualSign(String token) {
        return token.equals("=");
    }

    private boolean opensBlock(String token) {
        return token.equals("{");
    }

    private boolean closesBlock(String token) {
        return token.equals("}");
    }

    private boolean isRegularToken(String token) {
        return startsWithRegularCharacter(token);
    }

    private boolean startsWithRegularCharacter(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        char firstChar = token.charAt(0);
        return (firstChar >= 'A' && firstChar <= 'Z') ||
                (firstChar >= 'a' && firstChar <= 'z') ||
                (firstChar >= '0' && firstChar <= '9') ||
                (firstChar == '_');
    }
}

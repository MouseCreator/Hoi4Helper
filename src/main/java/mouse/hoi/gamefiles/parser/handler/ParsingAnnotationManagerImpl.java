package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
@Service
public class ParsingAnnotationManagerImpl implements ParsingAnnotationManager {

    private AnyKeyParsingAnnotationHandler anyKeyHandler;
    private ObjFieldParsingAnnotationHandler objFieldHandler;
    private SimpleParsingAnnotationHandler simpleHandler;
    private CollectionKeyParsingAnnotationHandler collectionKeyHandler;
    private UnexpectedTokensHandler unexpectedTokensHandler;
    private ParsingAnnotationHandler begin = null;
    @Autowired
    public void setAnyKeyHandler(AnyKeyParsingAnnotationHandler anyKeyHandler) {
        this.anyKeyHandler = anyKeyHandler;
    }
    @Autowired
    public void setObjFieldHandler(ObjFieldParsingAnnotationHandler objFieldHandler) {
        this.objFieldHandler = objFieldHandler;
    }
    @Autowired
    public void setSimpleHandler(SimpleParsingAnnotationHandler simpleHandler) {
        this.simpleHandler = simpleHandler;
    }
    @Autowired
    public void setCollectionKeyHandler(CollectionKeyParsingAnnotationHandler collectionKeyHandler) {
        this.collectionKeyHandler = collectionKeyHandler;
    }
    @Autowired
    public void setUnexpectedTokensHandler(UnexpectedTokensHandler unexpectedTokensHandler) {
        this.unexpectedTokensHandler = unexpectedTokensHandler;
    }

    @Override
    public void parseWithAnnotations(Object model, List<Property> propertyList) {
        begin.handle(model, propertyList, new ArrayList<>(propertyList));
    }
    @PostConstruct
    public void init() {
        inOrder(
                anyKeyHandler,
                simpleHandler,
                collectionKeyHandler,
                objFieldHandler,
                unexpectedTokensHandler
        );
    }

    private void inOrder(ParsingAnnotationHandler... handlers) {
        ParsingAnnotationHandler prev = null;
        for (ParsingAnnotationHandler handler : handlers) {
            if (prev != null) {
                prev.setNext(handler);
            } else {
                begin = handler;
            }
            prev = handler;
        }
    }
}

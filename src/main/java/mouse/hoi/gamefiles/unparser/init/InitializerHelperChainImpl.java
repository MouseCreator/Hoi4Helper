package mouse.hoi.gamefiles.unparser.init;

import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
@Service
public class InitializerHelperChainImpl implements InitializerHelperChain {

    private CommonInitializerHelper commonInitializerHelper;
    private DefaultInitializerHelper defaultInitializerHelper;
    private PrimitiveInitializerHelper primitiveInitializerHelper;
    private CollectionInitializerHelper collectionInitializerHelper;
    private InitializerHelper begin;
    @Autowired
    public void setCommonInitializerHelper(CommonInitializerHelper commonInitializerHelper) {
        this.commonInitializerHelper = commonInitializerHelper;
    }
    @Autowired
    public void setDefaultInitializerHelper(DefaultInitializerHelper defaultInitializerHelper) {
        this.defaultInitializerHelper = defaultInitializerHelper;
    }
    @Autowired
    public void setPrimitiveInitializerHelper(PrimitiveInitializerHelper primitiveInitializerHelper) {
        this.primitiveInitializerHelper = primitiveInitializerHelper;
    }
    @Autowired
    public void setCollectionInitializerHelper(CollectionInitializerHelper collectionInitializerHelper) {
        this.collectionInitializerHelper = collectionInitializerHelper;
    }

    @PostConstruct
    public void init() {
        setOrder(collectionInitializerHelper,
                primitiveInitializerHelper,
                defaultInitializerHelper,
                commonInitializerHelper);
    }

    private void setOrder(InitializerHelper... initializerHelpers) {
        InitializerHelper prev = null;
        for (InitializerHelper initializerHelper : initializerHelpers) {
            if (prev == null) {
                begin = initializerHelper;
                prev = initializerHelper;
                continue;
            }
            prev.setNext(initializerHelper);
            prev = initializerHelper;
        }
    }

    @Override
    public List<OutputProperty> initialize(OutputPropertyBuilder builder, Object model) {
        return begin.initialize(builder, model);
    }
}

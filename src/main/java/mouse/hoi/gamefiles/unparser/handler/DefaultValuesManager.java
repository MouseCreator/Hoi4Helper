package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.common.factorytype.FactoryType;
import mouse.hoi.gamefiles.common.modelcreator.ModelCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultValuesManager {
    private final ModelCreator modelCreator;
    @Autowired
    public DefaultValuesManager(ModelCreator modelCreator) {
        this.modelCreator = modelCreator;
    }

    public Object getDefault(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        if (clazz.equals(String.class)) {
            return "";
        } else if (clazz.equals(Integer.class)) {
            return 0;
        } else if (clazz.equals(Double.class)) {
            return 0.0;
        } else if (clazz.equals(Boolean.class)) {
            return false;
        } else {
            return modelCreator.lookup(clazz, FactoryType.TO_COMPARE);
        }
    }
}

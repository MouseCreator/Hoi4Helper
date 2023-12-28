package mouse.hoi.parser;

import mouse.hoi.factory.SimpleModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParsedModelCreator {

    private final ApplicationContext applicationContext;

    private final HashMap<Class<?>, SimpleModelFactory> map = new HashMap<>();

    @Autowired
    public ParsedModelCreator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @PostConstruct
    public void scanAndCreateFactoryMap() {

        Map<String, SimpleModelFactory> factoryBeans = applicationContext.getBeansOfType(SimpleModelFactory.class);

        for (SimpleModelFactory factory : factoryBeans.values()) {
            Class<?> modelClass = factory.getModelClass();
            map.put(modelClass, factory);
        }
    }

    public Object lookup(Class<?> className) {
        SimpleModelFactory simpleModelFactory = map.get(className);
        if (simpleModelFactory == null) {
            throw new IllegalStateException("No factory for class " + className + " provided");
        }
        return simpleModelFactory.get();
    }
}

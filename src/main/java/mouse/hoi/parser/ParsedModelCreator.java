package mouse.hoi.parser;

import mouse.hoi.parser.annotation.Factory;
import mouse.hoi.parser.annotation.FactoryFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class ParsedModelCreator implements ModelCreator {
    private final ApplicationContext applicationContext;

    private final HashMap<Class<?>, FactoryWithMethod> map = new HashMap<>();

    private final ParseHelper parseHelper;

    private record FactoryWithMethod(Method method, Object factory) {
    }

    @Autowired
    public ParsedModelCreator(ApplicationContext applicationContext, ParseHelper parseHelper) {
        this.applicationContext = applicationContext;
        this.parseHelper = parseHelper;
    }
    @PostConstruct
    public void scanAndCreateFactoryMap() {

        List<Object> factoryBeans = new ArrayList<>(applicationContext.getBeansWithAnnotation(Factory.class).values());
        for (Object factory : factoryBeans) {
            putFactoryMethodsInMap(map, factory);
        }
    }

    private void putFactoryMethodsInMap(HashMap<Class<?>, FactoryWithMethod> map, Object object) {
        List<Method> methods = parseHelper.getMethods(object);
        for (Method method : methods) {
            if (method.isAnnotationPresent(FactoryFor.class)) {
                Class<?> returnType = method.getReturnType();
                method.setAccessible(true);
                map.put(returnType, new FactoryWithMethod(method, object));
            }
        }

    }

    public Object lookup(Class<?> className) {
        FactoryWithMethod factoryWithMethod = map.get(className);
        if (factoryWithMethod == null) {
            throw new IllegalStateException("No factory for class " + className + " provided");
        }
        try {
            return factoryWithMethod.method.invoke(factoryWithMethod.factory);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to invoke factory method " + factoryWithMethod.method.getName()
                    + " for factory " + factoryWithMethod.factory.getClass().getSimpleName(), e);
        }
    }
}

package mouse.hoi.gamefiles.common.modelcreator;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.FactoryFor;
import mouse.hoi.gamefiles.common.annotation.Factory;
import mouse.hoi.gamefiles.common.factorytype.FactoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class ParsedModelCreator implements ModelCreator {
    private final ApplicationContext applicationContext;
    private final Map<Class<?>, Map<FactoryType, FactoryWithMethod>> map;
    private final ParseHelper parseHelper;
    private record FactoryWithMethod(Method method, Object factory) {
    }

    @Autowired
    public ParsedModelCreator(ApplicationContext applicationContext, ParseHelper parseHelper) {
        this.applicationContext = applicationContext;
        this.parseHelper = parseHelper;
        map = new HashMap<>();
    }
    @PostConstruct
    public void scanAndCreateFactoryMap() {
        List<Object> factoryBeans = new ArrayList<>(applicationContext.getBeansWithAnnotation(Factory.class).values());
        for (Object factory : factoryBeans) {
            putFactoryMethodsInMap(factory);
        }
    }

    private void putFactoryMethodsInMap(Object object) {
        List<Method> methods = parseHelper.getMethodsWithAnnotation(object, FactoryFor.class);
        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            method.setAccessible(true);
            FactoryFor annotation = method.getAnnotation(FactoryFor.class);
            Map<FactoryType, FactoryWithMethod> innerMap = map.computeIfAbsent(returnType, k -> new HashMap<>());
            for (FactoryType type : annotation.type()) {
                FactoryWithMethod other = innerMap.put(type, new FactoryWithMethod(method, object));
                if (other != null) {
                    throw new IllegalStateException("Multiple factories available for class " + returnType.getSimpleName()
                    + " and type " + type.name() + ": ");
                }
            }
        }

    }
    @Override
    public Object lookup(Class<?> className) {
        return lookup(className, FactoryType.DEFAULT);
    }

    @Override
    public Object lookup(Class<?> className, FactoryType factoryType) {
        FactoryWithMethod factoryWithMethod = getFactoryWithMethod(className, factoryType);
        try {
            return factoryWithMethod.method.invoke(factoryWithMethod.factory);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to invoke factory method " + factoryWithMethod.method.getName()
                    + " for factory " + factoryWithMethod.factory.getClass().getSimpleName(), e);
        }
    }

    private FactoryWithMethod getFactoryWithMethod(Class<?> className, FactoryType factoryType) {
        Map<FactoryType, FactoryWithMethod> innerMap = map.get(className);
        if (innerMap ==null) {
            throw new NoSuchElementException("No factory for class " + className + " provided");
        }
        FactoryWithMethod factoryWithMethod = innerMap.get(factoryType);
        if (factoryWithMethod == null) {
            factoryWithMethod = innerMap.get(FactoryType.DEFAULT);
            if (factoryWithMethod == null) {
                throw new NoSuchElementException("No default factory for class " + className + " provided");
            }
        }
        return factoryWithMethod;
    }
}

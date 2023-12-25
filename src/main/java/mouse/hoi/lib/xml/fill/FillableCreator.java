package mouse.hoi.lib.xml.fill;

import mouse.hoi.lib.scanner.PackageScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
@Component
public class FillableCreator {
    private final HashMap<String, Class<?>> map = new HashMap<>();

    private final PackageScanner packageScanner;
    @Autowired
    public FillableCreator(PackageScanner packageScanner) {
        this.packageScanner = packageScanner;
    }
    @PostConstruct
    void init() {
        scanPackage();
    }

    private void scanPackage() {
        String packageToScan = "mouse.hoi.config.fillable";
        List<Class<?>> fillableClasses = packageScanner.findAnnotatedClasses(packageToScan, Fillable.class);
        for (Class<?> fillableClass : fillableClasses) {
            Fillable fillableAnnotation = fillableClass.getAnnotation(Fillable.class);
            String name = fillableAnnotation.name();
            map.put(name, fillableClass);
        }
    }

    public boolean isElementDeclaration(String qName) {
        return map.containsKey(qName);
    }

    public Object createNew(String qName) {
        Class<?> myClass = map.get(qName);
        try {
            Constructor<?> constructor = myClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

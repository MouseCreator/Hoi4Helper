package mouse.hoi.lib.scanner;

import java.lang.annotation.Annotation;
import java.util.List;

public interface PackageScanner {
    List<Class<?>> findAnnotatedClasses(String packageToScan, Class<? extends Annotation> annotation);
}

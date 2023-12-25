package mouse.hoi.lib.scanner;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DefaultPackageScanner implements PackageScanner{
    @Override
    public List<Class<?>> findAnnotatedClasses(String packageToScan, Class<? extends Annotation> annotation) {
        List<Class<?>> annotatedClasses = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(annotation));

        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(packageToScan);
        for (org.springframework.beans.factory.config.BeanDefinition candidateComponent : candidateComponents) {
            try {
                Class<?> clazz = Class.forName(candidateComponent.getBeanClassName());
                annotatedClasses.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return annotatedClasses;
    }
}

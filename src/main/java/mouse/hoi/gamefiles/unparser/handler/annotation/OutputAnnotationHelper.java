package mouse.hoi.gamefiles.unparser.handler.annotation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public class OutputAnnotationHelper {

    public boolean hasAnnotation(List<Annotation> annotationList, Class<? extends Annotation> clazz) {
        for (Annotation annotation : annotationList) {
            if (annotation.getClass().equals(clazz))
                return true;
        }
        return false;
    }
    public <T extends Annotation> Optional<T> getAnnotation(List<Annotation> annotationList, Class<T> clazz) {
        for (Annotation annotation : annotationList) {
            if (annotation.getClass().equals(clazz))
                return Optional.of(clazz.cast(annotation));
        }
        return Optional.empty();
    }
}

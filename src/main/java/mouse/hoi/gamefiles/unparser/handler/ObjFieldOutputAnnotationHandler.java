package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Service
public class ObjFieldOutputAnnotationHandler implements OutputAnnotationHandler {

    private final OutputAnnotationHelper helper;
    @Autowired
    public ObjFieldOutputAnnotationHandler(OutputAnnotationHelper helper) {
        this.helper = helper;
    }

    @Override
    public boolean canHandle(OutputPropertyBuilder builder, List<Annotation> annotations, Object model) {
        return helper.hasAnnotation(annotations, ObjField.class);
    }

    @Override
    public void handle(OutputPropertyBuilder builder, List<Annotation> annotations, Object model) {
        Optional<ObjField> objFieldAnnotation = helper.getAnnotation(annotations, ObjField.class);
        assert objFieldAnnotation.isPresent();
        String key = objFieldAnnotation.get().text();
        builder.withKey(key);
    }
}

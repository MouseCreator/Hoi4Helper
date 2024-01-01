package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class ObjFieldAnnotationHelper implements BuilderAnnotationHelper {

    private final ParseHelper parseHelper;
    private final BuilderInitializer builderInitializer;

    public ObjFieldAnnotationHelper(ParseHelper parseHelper, BuilderInitializer builderInitializer) {
        this.parseHelper = parseHelper;
        this.builderInitializer = builderInitializer;
    }

    @Override
    public List<OutputProperty> toProperties(Object model) {

        List<Field> objFields = parseHelper.getFieldsWithAnnotation(model, ObjField.class);
        HashMap<String, List<Field>> fieldsByKeys = toObjFieldMap(objFields);
        List<OutputProperty> result = new ArrayList<>();
        for (String key : fieldsByKeys.keySet()) {
            List<Field> fields = fieldsByKeys.get(key);
            ForEachBuilder consumer = b -> b.withKey(key);
            List<OutputProperty> properties = builderInitializer.initializeProperties(model, fields, consumer);
            result.addAll(properties);
        }
        return result;
    }

    private HashMap<String, List<Field>> toObjFieldMap(List<Field> fields) {
        HashMap<String, List<Field>> map = new HashMap<>();
        for (Field field : fields) {
            String key = field.getAnnotation(ObjField.class).text();
            List<Field> forKey = map.computeIfAbsent(key, k -> new ArrayList<>());
            forKey.add(field);
        }
        return map;
    }
}

package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.CollectionKey;
import mouse.hoi.gamefiles.common.collectiontype.CollectionType;
import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class CollectionKeyAnnotationHelper implements BuilderAnnotationHelper {
    private final ParseHelper parseHelper;
    private final BuilderInitializer builderInitializer;

    public CollectionKeyAnnotationHelper(ParseHelper parseHelper, BuilderInitializer builderInitializer) {
        this.parseHelper = parseHelper;
        this.builderInitializer = builderInitializer;
    }

    @Override
    public List<OutputProperty> toProperties(Object model) {
        List<Field> objFields = parseHelper.getFieldsWithAnnotation(model, CollectionKey.class);
        HashMap<CollectionType, List<Field>> fieldsByKeys = toObjFieldMap(objFields);
        List<OutputProperty> result = new ArrayList<>();
        for (CollectionType key : fieldsByKeys.keySet()) {
            List<Field> fields = fieldsByKeys.get(key);
            result.addAll(builderInitializer.initializeProperties(model, fields));
        }
        return result;
    }

    private HashMap<CollectionType, List<Field>> toObjFieldMap(List<Field> fields) {
        HashMap<CollectionType, List<Field>> map = new HashMap<>();
        for (Field field : fields) {
            CollectionType key = field.getAnnotation(CollectionKey.class).type();
            List<Field> forKey = map.computeIfAbsent(key, k -> new ArrayList<>());
            forKey.add(field);
        }
        return map;
    }
}

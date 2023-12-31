package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.unparser.CreationParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
@Service
public class CreationParametersFactory {

    private final ParseHelper parseHelper;
    @Autowired
    public CreationParametersFactory(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
    }

    public CreationParameters create(Field field) {
        CreationParameters creationParameters = new CreationParameters();
        creationParameters.getFieldAnnotations().addAll(parseHelper.getAnnotations(field));
        creationParameters.getClassAnnotations().addAll(Arrays.asList(field.getType().getAnnotations()));
        return creationParameters;
    }
}

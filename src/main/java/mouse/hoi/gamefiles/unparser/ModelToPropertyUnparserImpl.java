package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.parser.ModelCreator;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

@Service
public class ModelToPropertyUnparserImpl implements ModelToPropertyUnparser {

    private ParseHelper parseHelper;
    private ModelCreator modelCreator;
    @Override
    public OutputProperty unparse(Object model, OutputPropertyBuilder outputPropertyBuilder) {
        return null;
    }
}

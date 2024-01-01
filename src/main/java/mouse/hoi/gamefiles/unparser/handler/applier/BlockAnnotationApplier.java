package mouse.hoi.gamefiles.unparser.handler.applier;

import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

@Service
public class BlockAnnotationApplier implements BuilderAnnotationApplier{
    @Override
    public void apply(OutputPropertyBuilder builder, Object model) {
        Block annotation = model.getClass().getAnnotation(Block.class);
        if (annotation == null) {
            return;
        }
        String key = annotation.name();
        if (builder.hasNoKey()) {
            builder.withKey(key);
        }
    }
}

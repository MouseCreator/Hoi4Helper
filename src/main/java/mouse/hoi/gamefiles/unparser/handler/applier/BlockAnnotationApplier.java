package mouse.hoi.gamefiles.unparser.handler.applier;

import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlockAnnotationApplier implements BuilderAnnotationApplier{
    @Override
    public void apply(OutputPropertyBuilder builder, Object model) {
        Optional<Block> annotation = builder.getAnnotation(Block.class);
        if (annotation.isEmpty()) {
            return;
        }
        String key = annotation.get().name();
        if (builder.hasNoKey()) {
            builder.withKey(key);
        }
    }
}

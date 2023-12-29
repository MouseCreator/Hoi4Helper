package mouse.hoi.factory;

import mouse.hoi.model.DoublePosition;
import mouse.hoi.model.texture.Animation;
import mouse.hoi.parser.annotation.Factory;
import mouse.hoi.parser.annotation.FactoryFor;
import mouse.hoi.util.DefConstants;
import org.springframework.stereotype.Component;

@Component
@Factory
public class AnimationFactory {

    private final DefConstants defConstants;

    public AnimationFactory(DefConstants defConstants) {
        this.defConstants = defConstants;
    }
    @FactoryFor
    public Animation getAnimation() {
        Animation animation = new Animation();
        animation.setMaskFile(null);
        animation.setTextureFile(defConstants.getDefaultOverlay());
        animation.setRotation(0.0);
        animation.setLooping(false);
        animation.setTimeSeconds(0.75);
        animation.setDelaySeconds(0.0);
        animation.setBlendMode("add");
        animation.setType("scrolling");
        animation.setRotationOffset(DoublePosition.zeros());
        animation.setTextureScale(DoublePosition.ones());
        return animation;
    }
}

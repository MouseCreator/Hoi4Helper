package mouse.hoi.factory;

import mouse.hoi.model.DoublePosition;
import mouse.hoi.model.texture.Animation;
import mouse.hoi.util.DefConstants;
import org.springframework.stereotype.Component;

@Component
public class AnimationFactory implements SimpleModelFactory {

    private final DefConstants defConstants;

    public AnimationFactory(DefConstants defConstants) {
        this.defConstants = defConstants;
    }

    public Animation get() {
        Animation animation = new Animation();
        animation.setMaskFile(null);
        animation.setTextureFile(defConstants.getDefaultOverlay());
        animation.setRotation(-90.0);
        animation.setLooping(false);
        animation.setTimeSeconds(0.75);
        animation.setDelaySeconds(0.0);
        animation.setBlendMode("add");
        animation.setType("scrolling");
        animation.setRotationOffset(DoublePosition.zeros());
        animation.setTextureScale(DoublePosition.ones());
        return animation;
    }

    @Override
    public Class<?> getModelClass() {
        return Animation.class;
    }
}

package mouse.hoi.factory;

import mouse.hoi.gamefiles.tempmodel.DoublePosition;
import mouse.hoi.gamefiles.tempmodel.texture.Animation;
import mouse.hoi.gamefiles.common.annotation.Factory;
import mouse.hoi.gamefiles.common.annotation.FactoryFor;
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

package mouse.hoi.parser;

import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.factory.AnimationFactory;
import mouse.hoi.factory.SpriteTypeFactory;
import mouse.hoi.gamefiles.parser.GameFileParser;
import mouse.hoi.lib.filemanager.FileManager;
import mouse.hoi.gamefiles.tempmodel.DoublePosition;
import mouse.hoi.gamefiles.tempmodel.texture.Animation;
import mouse.hoi.gamefiles.tempmodel.texture.SpriteType;
import mouse.hoi.gamefiles.tempmodel.texture.SpriteTypes;
import mouse.hoi.gamefiles.parser.parse.FileFormatter;
import mouse.hoi.gamefiles.parser.parse.PropertyParser;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.gamefiles.parser.property.Property;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFileParserTest {

    private SpriteTypes createSample(ApplicationContext context) {
        AnimationFactory animationFactory = context.getBean(AnimationFactory.class);
        SpriteTypeFactory spriteTypeFactory = context.getBean(SpriteTypeFactory.class);
        SpriteTypes spriteTypes = spriteTypeFactory.getSpriteTypes();

        SpriteType spriteType1 = spriteTypeFactory.getSpriteType();
        spriteType1.setName("GFX__shine");
        spriteType1.setTextureFile("gfx/interface/goals/goal_unknown.dds");
        spriteType1.setEffectFile("gfx/FX/buttonstate.lua");
        spriteType1.setLazyLoad(false);

        SpriteType spriteType2 = spriteTypeFactory.getSpriteType();
        spriteType2.setName("GFX_goal_continuous_air_production_shine");
        spriteType2.setTextureFile("gfx/interface/goals/goal_continuous_air_production.dds");
        spriteType2.setEffectFile("gfx/FX/buttonstate.lua");
        spriteType2.setLazyLoad(false);

        Animation animation1 = createAnimation(animationFactory, "gfx/interface/goals/goal_unknown.dds");
        animation1.setRotation(-90.0);
        Animation animation2 = createAnimation(animationFactory, "gfx/interface/goals/goal_unknown.dds");
        animation2.setRotation(90.0);

        Animation animation3 = createAnimation(animationFactory, "gfx/interface/goals/goal_continuous_air_production.dds");
        animation3.setRotation(-90.0);

        Animation animation4 = createAnimation(animationFactory, "gfx/interface/goals/goal_continuous_air_production.dds");
        animation4.setRotation(90.0);
        spriteType1.getAnimationList().addAll(List.of(animation1, animation2));
        spriteType2.getAnimationList().addAll(List.of(animation3, animation4));

        List<SpriteType> spriteTypeList = List.of(spriteType1, spriteType2);
        spriteTypes.getSpriteTypes().addAll(spriteTypeList);
        return spriteTypes;
    }

    private static Animation createAnimation(AnimationFactory animationFactory, String mask) {
        Animation animation = animationFactory.getAnimation();
        animation.setMaskFile(mask);
        animation.setLooping(false);
        animation.setTimeSeconds(0.75);
        animation.setDelaySeconds(0.0);
        animation.setBlendMode("add");
        animation.setType("scrolling");
        animation.setRotationOffset(DoublePosition.zeros());
        animation.setTextureScale(DoublePosition.ones());
        return animation;
    }

    @Test
    void parseFrom() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParser gameFileParser = context.getBean(GameFileParser.class);
        PropertyParser propertyParser = context.getBean(PropertyParser.class);
        FileFormatter formatter = context.getBean(FileFormatter.class);
        FileManager fileManager = context.getBean(FileManager.class);
        String content = fileManager.read("src/test/resources/assets/parse/SampleSpriteTypes_01.txt");
        List<Property> properties = propertyParser.parse(new TokenCollection(formatter.formatAndTokenize(content)));
        List<SpriteTypes> parsingResult = gameFileParser.parseFrom(SpriteTypes.class, properties);
        assertEquals(1, parsingResult.size());
        SpriteTypes spriteTypes = parsingResult.get(0);

        List<SpriteType> spriteTypeList = spriteTypes.getSpriteTypes();
        assertEquals(2, spriteTypeList.size());
        SpriteTypes expected = createSample(context);
        assertEquals(expected, spriteTypes);
    }
}
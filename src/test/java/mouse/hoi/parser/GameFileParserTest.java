package mouse.hoi.parser;

import mouse.hoi.config.GeneralUtils;
import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.model.texture.SpriteTypes;
import mouse.hoi.parser.parse.FileFormatter;
import mouse.hoi.parser.parse.PropertyParser;
import mouse.hoi.parser.parse.TokenCollection;
import mouse.hoi.parser.property.Property;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFileParserTest {

    @Test
    void parseFrom() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParser gameFileParser = context.getBean(GameFileParser.class);
        PropertyParser propertyParser = context.getBean(PropertyParser.class);
        FileFormatter formatter = context.getBean(FileFormatter.class);

        String content = """
                spriteTypes = {	
                                
                                
                                
                	SpriteType = {
                		name = "GFX__shine"
                		texturefile = "gfx/interface/goals/goal_unknown.dds"
                				effectFile = "gfx/FX/buttonstate.lua"
                		animation = {
                			animationmaskfile = "gfx/interface/goals/goal_unknown.dds"
                			animationtexturefile = "gfx/interface/goals/shine_overlay.dds" 	# <- the animated file
                			animationrotation = -90.0		# -90 clockwise 90 counterclockwise(by default)
                			animationlooping = no			# yes or no ;)
                			animationtime = 0.75				# in seconds
                			animationdelay = 0			# in seconds
                			animationblendmode = "add"       #add, multiply, overlay
                			animationtype = "scrolling"      #scrolling, rotating, pulsing
                			animationrotationoffset = { x = 0.0 y = 0.0 }
                			animationtexturescale = { x = 1.0 y = 1.0 }\s
                		}
                                
                		animation = {
                			animationmaskfile = "gfx/interface/goals/goal_unknown.dds"
                			animationtexturefile = "gfx/interface/goals/shine_overlay.dds" 	# <- the animated file
                			animationrotation = 90.0		# -90 clockwise 90 counterclockwise(by default)
                			animationlooping = no			# yes or no ;)
                			animationtime = 0.75				# in seconds
                			animationdelay = 0			# in seconds
                			animationblendmode = "add"       #add, multiply, overlay
                			animationtype = "scrolling"      #scrolling, rotating, pulsing
                			animationrotationoffset = { x = 0.0 y = 0.0 }
                			animationtexturescale = { x = 1.0 y = 1.0 }\s
                		}
                		legacy_lazy_load = no
                	}
                	#### Generic continuous goal icons
                                
                	SpriteType = {
                		name = "GFX_goal_continuous_air_production_shine"
                		texturefile = "gfx/interface/goals/goal_continuous_air_production.dds"			
                		effectFile = "gfx/FX/buttonstate.lua"
                		animation = {
                			animationmaskfile = "gfx/interface/goals/goal_continuous_air_production.dds"			
                			animationtexturefile = "gfx/interface/goals/shine_overlay.dds" 	# <- the animated file
                			animationrotation = -90.0		# -90 clockwise 90 counterclockwise(by default)
                			animationlooping = no			# yes or no ;)
                			animationtime = 0.75				# in seconds
                			animationdelay = 0			# in seconds
                			animationblendmode = "add"       #add, multiply, overlay
                			animationtype = "scrolling"      #scrolling, rotating, pulsing
                			animationrotationoffset = { x = 0.0 y = 0.0 }
                			animationtexturescale = { x = 1.0 y = 1.0 }\s
                		}
                                
                		animation = {
                			animationmaskfile = "gfx/interface/goals/goal_continuous_air_production.dds"			
                			animationtexturefile = "gfx/interface/goals/shine_overlay.dds" 	# <- the animated file
                			animationrotation = 90.0		# -90 clockwise 90 counterclockwise(by default)
                			animationlooping = no			# yes or no ;)
                			animationtime = 0.75				# in seconds
                			animationdelay = 0			# in seconds
                			animationblendmode = "add"       #add, multiply, overlay
                			animationtype = "scrolling"      #scrolling, rotating, pulsing
                			animationrotationoffset = { x = 0.0 y = 0.0 }
                			animationtexturescale = { x = 1.0 y = 1.0 }\s
                		}
                		legacy_lazy_load = no
                	}
                }
                """;

        List<Property> properties = propertyParser.parse(new TokenCollection(formatter.formatAndTokenize(content)));
        SpriteTypes spriteTypes = gameFileParser.parseFrom(SpriteTypes.class, properties);
        System.out.println(spriteTypes);
    }
}
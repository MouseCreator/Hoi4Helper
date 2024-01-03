package mouse.hoi.transform;

import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.gamefiles.factory.SpriteTypeFactory;
import mouse.hoi.gamefiles.tempmodel.interfacetexture.SpriteTypes;
import mouse.hoi.gamefiles.transform.SimpleCollectionManager;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCollectionManagerImplTest {

    @Test
    void merge() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        SimpleCollectionManager simpleCollectionManager =
               context.getBean(SimpleCollectionManager.class);
        SpriteTypeFactory factory = context.getBean(SpriteTypeFactory.class);
        SpriteTypes spriteTypes1 = factory.getSpriteTypes();

        spriteTypes1.getSpriteTypes().add(factory.getSpriteType());
        spriteTypes1.getSpriteTypes().add(factory.getSpriteType());

        SpriteTypes spriteTypes2 = factory.getSpriteTypes();

        spriteTypes2.getSpriteTypes().add(factory.getSpriteType());
        SpriteTypes merge = simpleCollectionManager.mergeLists(SpriteTypes.class, List.of(spriteTypes1, spriteTypes2));
        assertEquals(3, merge.getSpriteTypes().size());
    }
}
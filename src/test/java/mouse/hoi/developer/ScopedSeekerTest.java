package mouse.hoi.developer;

import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.developer.searcher.ScopedSeeker;
import mouse.hoi.gamefiles.parser.property.Property;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

class ScopedSeekerTest {

    @Test
    void lookForTriggerDeclarations() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        ScopedSeeker scopedSeeker = context.getBean(ScopedSeeker.class);
        List<Property> properties = scopedSeeker.lookForTriggerDeclarations("D:\\SteamLibrary\\steamapps\\common\\Hearts of Iron IV\\common\\national_focus");
        System.out.println(printStyled(properties));
    }

    String printStyled(List<Property> properties) {
        StringBuilder builder = new StringBuilder();
        for (Property property : properties) {
            printStyled(builder, property);
        }
        return builder.toString();
    }

    private void printStyled(StringBuilder builder, Property property) {
        builder.append(property.getKey())
                .append(" = ")
                .append(property.getValue());
        if (property.isBlock()) {
            builder.append("{");
            for (Property child : property.getChildren()) {
                builder.append(child.getKey()).append(" ");
            }
            builder.append("}");
        }
        builder.append("\n");
    }
}
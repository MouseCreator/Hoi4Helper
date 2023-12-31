package mouse.hoi.gamefiles.unparser.unparsing;

import mouse.hoi.gamefiles.common.style.PrintStyle;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyToStringUnparserImplTest {
    private PropertyToStringUnparser unparser;

    @BeforeEach
    void setUp() {
        unparser = new PropertyToStringUnparserImpl();
    }

    private OutputProperty sampleProperty() {
        OutputProperty simple1 = new OutputPropertyBuilder().createSimple("Simple_1");
        OutputProperty simple2 = new OutputPropertyBuilder().createSimple("Simple_2");
        OutputProperty simples = new OutputPropertyBuilder()
                .withKey("Simples").withValue("BV").withChildren(List.of(simple1, simple2)).withStyle(PrintStyle.SIMPLE).block();

        OutputProperty simple3 = new OutputPropertyBuilder().createSimple("Simple_3");
        OutputProperty simple4 = new OutputPropertyBuilder().createSimple("Simple_4");
        OutputProperty single = new OutputPropertyBuilder()
                .withKey("Other").withChildren(List.of(simple3, simple4)).withStyle(PrintStyle.MEDIUM).block();
        OutputProperty fv1 = new OutputPropertyBuilder()
                .withKey("Key").withValue("Value").fieldValue();
        OutputProperty fv2 = new OutputPropertyBuilder()
                .withKey("A").withValue("B").fieldValue();

        OutputProperty fieldValues = new OutputPropertyBuilder()
                .withKey("FV").withChildren(List.of(fv1, fv2)).withStyle(PrintStyle.COMPLEX).block();

        return new OutputPropertyBuilder()
                .withKey("Main").withChildren(List.of(simples, single, fieldValues)).block();
    }
    @Test
    void unparse() {
        OutputProperty bigBlock = sampleProperty();
        OutputProperty smallMessage = new OutputPropertyBuilder().withKey("Empty").block();
        String properties = unparser.unparse(List.of(bigBlock, smallMessage));

        String expected = """
                Main = {
                \tSimples = BV { Simple_1 Simple_2 }
                \tOther = {
                \t\tSimple_3 Simple_4
                \t}
                \tFV = {
                \t\tKey = Value
                \t\tA = B
                \t}
                }
                Empty = {
                }
                """;

        assertEquals(expected, properties);
    }
}
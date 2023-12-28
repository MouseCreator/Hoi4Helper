package mouse.hoi.parser;


import mouse.hoi.parser.property.Property;


public interface PropertyToModelParser {
    Object getModel(Class<?> tClass, Property property);
}

package mouse.hoi.gamefiles.parser;


import mouse.hoi.gamefiles.parser.property.Property;


public interface PropertyToModelParser {
    Object getModel(Class<?> tClass, Property property);
}

package mouse.hoi.gamefiles.parser;

import mouse.hoi.gamefiles.common.factorytype.FactoryType;

public interface ModelCreator {
    Object lookup(Class<?> className);
    Object lookup(Class<?> className, FactoryType factoryType);
}

package mouse.hoi.gamefiles.common.modelcreator;

import mouse.hoi.gamefiles.common.factorytype.FactoryType;

public interface ModelCreator {
    Object lookup(Class<?> className);
    Object lookup(Class<?> className, FactoryType factoryType);
}

package mouse.hoi.gamefiles.factory;

import mouse.hoi.gamefiles.common.annotation.Factory;
import mouse.hoi.gamefiles.common.annotation.FactoryFor;
import mouse.hoi.gamefiles.tempmodel.common.resources.Resource;
import mouse.hoi.gamefiles.tempmodel.common.resources.Resources;
import org.springframework.stereotype.Component;

@Component
@Factory
public class ResourcesFactory {
    @FactoryFor
    public Resources getResources() {
        return new Resources();
    }

    @FactoryFor
    public Resource getResource() {
        Resource resource = new Resource();
        resource.setName("");
        resource.setCic(0.125);
        resource.setConvoys(0.1);
        return resource;
    }
}

package mouse.hoi.gamefiles.tempmodel.common.resources;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.AnyKey;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.tempmodel.extras.SimpleCollection;

import java.util.Collection;
import java.util.List;

@Block(name="resources")
@Data
public class Resources implements SimpleCollection<Resource> {
    @AnyKey
    private List<Resource> resourceList;
    @Override
    public Collection<Resource> getCollection() {
        return resourceList;
    }
}

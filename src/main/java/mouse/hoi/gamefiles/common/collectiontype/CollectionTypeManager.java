package mouse.hoi.gamefiles.common.collectiontype;

import java.util.List;

public interface CollectionTypeManager {
    List<CollectionType> findTypeForToken(String token);
}

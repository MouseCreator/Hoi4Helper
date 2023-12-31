package mouse.hoi.gamefiles.common.collectiontype;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MockCollectionTypeManager implements CollectionTypeManager {

    @Override
    public List<CollectionType> findTypeForToken(String token) {
        if (isInteger(token)) {
            return List.of(CollectionType.STATE_ID, CollectionType.PROVINCE_ID, CollectionType.STRATEGIC_REGION_ID);
        }
        CollectionType type = toSingleElement(token);
        if (type == null) {
            return List.of();
        }
        return List.of(type);

    }

    private CollectionType toSingleElement(String token) {
        return switch (token) {
            case "rubber", "aluminium", "tungsten", "oil", "chromium", "steel" -> CollectionType.RESOURCE;
            case "industrial_complex", "dockyard", "infrastructure", "arms_factory", "bunker",
                    "coastal_bunker", "naval_base", "air_base" -> CollectionType.BUILDING;
            case "FROM", "ROOT" -> CollectionType.TAG;
            case "plains", "mountain", "urban", "forest", "hills", "marsh", "desert", "jungle" -> CollectionType.TERRAIN;
            default -> null;
        };
    }

    private boolean isInteger(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

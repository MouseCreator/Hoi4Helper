package mouse.hoi.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DefConstants {
    private String defaultOverlay = "gfx/interface/goals/shine_overlay.dds";
    private String defaultFXLua = "gfx/FX/buttonstate.lua";
}

package mouse.hoi.gamefiles.tempmodel.common.resources;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.FromKeyValue;
import mouse.hoi.gamefiles.common.annotation.ObjField;

@Block(name = "resource")
@Data
public class Resource {
    @FromKeyValue
    private String name;
    @ObjField(text = "icon_frame")
    private int iconFrame;

    @ObjField(text = "cic")
    private double cic;
    @ObjField(text = "convoys")
    private double convoys;
}

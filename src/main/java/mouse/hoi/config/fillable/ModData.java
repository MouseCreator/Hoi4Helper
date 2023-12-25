package mouse.hoi.config.fillable;

import lombok.Data;
import mouse.hoi.lib.xml.fill.Fill;
import mouse.hoi.lib.xml.fill.Fillable;

@Fillable(name = "mod-data")
@Data
public class ModData {
    @Fill(attribute = "location")
    private String modLocation;
}

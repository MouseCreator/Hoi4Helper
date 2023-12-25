package mouse.hoi.config;

public class ModData {
    private static ModData modData = null;
    private ModData() {

    }
    public static ModData get() {
        if (modData == null) {
            modData = new ModData();
        }
        return modData;
    }

    public String getModLocation() {
        return "C:\\Users\\mysha\\Documents\\Paradox Interactive\\Hearts of Iron IV\\mod\\gubbdA";
    }
}

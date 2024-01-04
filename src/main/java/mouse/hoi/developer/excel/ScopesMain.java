package mouse.hoi.developer.excel;

import mouse.hoi.developer.excel.sheet.CustomSheet;

import java.io.IOException;

public class ScopesMain {
    public static void main(String[] args) throws IOException {
        XSLXReader XSLXReader = new XSLXReader();
        try {
            CustomSheet content = XSLXReader.read("src/main/resources/developer/xlsx/DualScopes.xlsx", 0);

        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

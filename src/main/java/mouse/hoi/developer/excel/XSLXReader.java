package mouse.hoi.developer.excel;


import mouse.hoi.developer.excel.sheet.CustomSheet;
import mouse.hoi.developer.excel.sheet.SheetBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XSLXReader {
    public CustomSheet read(String filename, int identifier) throws IOException {
        FileInputStream file = new FileInputStream(filename);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(identifier);
        SheetBuilder builder = new SheetBuilder();
        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING -> builder.insert(cell.getStringCellValue());
                    case NUMERIC -> builder.insert(cell.getNumericCellValue());
                    case BOOLEAN -> builder.insert(cell.getBooleanCellValue());
                    default -> {
                    }
                }
            }
            builder.nextRow();
        }
        file.close();
        return builder.get();
    }
}

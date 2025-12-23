package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public static void writeCsv(String id) {
        try {
            String excelPath =System.getProperty("user.dir"+ "/src/main/resources/indent-Material-PAND-ref9049-M-5-2025-12-22T19_48_52.064Z.xlsx");
            FileInputStream fis = new FileInputStream(excelPath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Materials");
            Row row = sheet.getRow(1);
            Cell cell = row.getCell(9);
            cell.setCellValue("Dummydepot" + id);
            Cell cell1 = row.getCell(10);
            cell1.setCellValue("consignee" + id);
            Cell cell2 = row.getCell(11);
            cell2.setCellValue("consignee" + id);
            fis.close();
            FileOutputStream fos = new FileOutputStream(excelPath);
            workbook.write(fos);
            fos.close();
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
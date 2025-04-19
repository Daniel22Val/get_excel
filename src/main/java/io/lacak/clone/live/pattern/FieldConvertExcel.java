package io.lacak.clone.live.pattern;

import io.lacak.clone.live.ui.model.FieldBase;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FieldConvertExcel <T extends FieldBase> {
    private T emptyObject;

    public FieldConvertExcel(T emptyObject) {
        this.emptyObject = emptyObject;
    }

    public XSSFWorkbook exportReport(List<T> reports){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("SUMMARY");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat((short) 21);

        writeHeader(emptyObject,sheet);

        if (reports != null && reports.isEmpty() == false )
            writeReportData(reports,sheet,cellStyle);

        return workbook;
    }

    public XSSFWorkbook exportReportAnotherSheet(XSSFWorkbook workbook, String sheetName, List<T> reports){
        XSSFSheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat((short) 21);

        writeHeader(emptyObject,sheet);

        if (reports != null && reports.isEmpty() == false )
            writeReportData(reports,sheet,cellStyle);

        return workbook;
    }

    private XSSFSheet writeHeader(T report, XSSFSheet sheet){
        Row row = sheet.createRow(0);
        Cell cell = null;
        Field[] fields = report.getClass().getDeclaredFields();

        for(int i=0; i<fields.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(fields[i].getName());
        }

        return sheet;
    }

    private void writeReportData(List<T> reports, XSSFSheet sheet, CellStyle cellStyle){
        int rowCount = 1;

        for(T report: reports){
            Row row = sheet.createRow(rowCount++);
            Field[] fields = report.getClass().getDeclaredFields();
            Cell cell = null;

            for (int fieldCount = 0; fieldCount < fields.length; fieldCount++) {
                Field field = fields[fieldCount];
                Object value = report.getVariable(field);

                cell = row.createCell(fieldCount);
                cell.setCellValue(String.valueOf(value));
            }
        }
    }
}

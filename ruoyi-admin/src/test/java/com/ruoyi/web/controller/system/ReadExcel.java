package com.ruoyi.web.controller.system;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadExcel {

    public static List<Info> readExcel(String filePath, String sheetName) {
        List<Info> infoList = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);

            for (Row row : sheet) {
                Cell cellB = row.getCell(1); // B 列
                Cell cellC = row.getCell(2); // C 列

                if (cellB != null && cellC != null) {
                    String name = cellB.getStringCellValue();
                    String count = String.valueOf(cellC.getNumericCellValue());
                    boolean isRed = hasRedFontColor(cellC);

                    Info info = new Info(name, count, isRed);
                    infoList.add(info);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }

    public static boolean hasRedFontColor(Cell cell) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = cell.getCellStyle();
        Font font = workbook.getFontAt(cellStyle.getFontIndex());
        XSSFFont f = (XSSFFont) font;//获取字体
        XSSFColor xssffont = f.getXSSFColor();
        if (xssffont != null) {
            byte[] rgb;
            rgb = xssffont.getRGB(); //得到rgb的byte数组
            String color = String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
            if(color.equals("#FF0000")){
                return true;
            }
        }

        return false;
    }


    public static class Info {
        private String name;
        private String count;

        private boolean type;

        public Info(String name, String count) {
            this.name = name;
            this.count = count;
        }

        public Info(String name, String count, boolean type) {
            this.name = name;
            this.count = count;
            this.type = type;
        }

        public boolean getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getCount() {
            return count;
        }
    }
}


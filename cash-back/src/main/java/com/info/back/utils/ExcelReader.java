package com.info.back.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    // Logger
    private static Logger logger = LoggerFactory.getLogger(ExcelReader.class);


    private XSSFWorkbook wb = null;// book [includes sheet]

    private XSSFSheet sheet = null;

    private XSSFRow row = null;

    private int sheetNum = 0; // 第sheetnum个工作表

    private int rowNum = 0;

    private InputStream fis = null;

    private File file = null;

    public ExcelReader() {
    }

    public ExcelReader(File file) {
        this.file = file;
    }
    
    public ExcelReader(InputStream is) {
        fis = is;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 读取excel文件获得HSSFWorkbook对象
     */
    public void open() throws IOException {
        
        if(fis==null) {
            fis = new FileInputStream(file);
        }
        wb = new XSSFWorkbook(fis);
        fis.close();
    }

    /**
     * 获取文件流
     * @param fis
     * @throws IOException
     */
    public void open(InputStream fis) throws IOException{
        wb = new XSSFWorkbook(fis);
        fis.close();
    }

    /**
     * 返回sheet表数目
     * 
     * @return int
     */
    public int getSheetCount() {
        int sheetCount = -1;
        sheetCount = wb.getNumberOfSheets();
        return sheetCount;
    }

    /**
     * sheetNum下的记录行数
     * 
     * @return int
     */
    public int getRowCount() {
        if (wb == null)
            logger.info("=============>WorkBook为空");
        XSSFSheet sheet = wb.getSheetAt(this.sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /**
     * 读取指定sheetNum的rowCount
     * 
     * @param sheetNum
     * @return int
     */
    public int getRowCount(int sheetNum) {
        XSSFSheet sheet = wb.getSheetAt(sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /**
     * 得到指定行的内容
     * 
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int lineNum) {
        return readExcelLine(this.sheetNum, lineNum);
    }

    /**
     * 指定工作表和行数的内容
     * 
     * @param sheetNum
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int sheetNum, int lineNum) {
        if (sheetNum < 0 || lineNum < 0)
            return null;
        String[] strExcelLine = null;
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(lineNum);
            if (row == null)
            {
                return strExcelLine;
            }
            int cellCount = row.getLastCellNum();
            strExcelLine = new String[cellCount + 1];
            for (int i = 0; i <= cellCount; i++) {
                strExcelLine[i] = readStringExcelCell(lineNum, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelLine;
    }

    /**
     * 读取指定列的内容
     * 
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int cellNum) {
        return readStringExcelCell(this.rowNum, cellNum);
    }

    /**
     * 指定行和列编号的内容
     * 
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int rowNum, int cellNum) {
        return readStringExcelCell(this.sheetNum, rowNum, cellNum);
    }

    /**
     * 指定工作表、行、列下的内容
     * 
     * @param sheetNum
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
        if (sheetNum < 0 || rowNum < 0)
            return "";
        String strExcelCell = "";
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(rowNum);

            if (row.getCell(cellNum) != null) { // add this condition
                // judge
                switch (row.getCell(cellNum).getCellType()) {
                    case HSSFCell.CELL_TYPE_FORMULA:
                        strExcelCell = "FORMULA ";
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        row.getCell(cellNum).setCellType(HSSFCell.CELL_TYPE_STRING);
                        strExcelCell = row.getCell(cellNum).getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        strExcelCell = row.getCell(cellNum).getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                        strExcelCell = "";
                        break;
                    default:
                        strExcelCell = "";
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelCell;
    }

    public static void main(String args[]) {
        File file = new File("F:\\pho.xlsx");
        ExcelReader readExcel = new ExcelReader(file);
        try {
            readExcel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readExcel.setSheetNum(0); // 设置读取索引为0的工作表
        // 总行数
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        int count = readExcel.getRowCount();
        for (int i = 0; i <= count; i++) {
            String[] rows = readExcel.readExcelLine(i);
            Map<String,Object> map = new HashMap<String,Object>();
            for (int j = 0; j < rows.length; j++) {
                if(StringUtils.isNotEmpty(rows[j])){
                    map.put("userPhone",rows[j]);
//                    System.out.print(rows[j] + " ");
                }
            }
            list.add(map);

        }
        System.out.print(list);
    }

    public static void setFileDownloadHeader(HttpServletRequest request,
                                             HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        String finalFileName = fileName;
        try {
            if (org.apache.commons.lang.StringUtils.contains(userAgent, "firefox")) {// 火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + finalFileName + "\"");// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        } catch (Exception e) {
            logger.error("setFileDownloadHeader error", e);
        }
    }
}
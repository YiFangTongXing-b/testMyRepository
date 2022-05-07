package com.lihan.scorelinequery.Util;

import com.lihan.scorelinequery.entity.AdmissionScore;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class AdmissionExcelUtil {
    public static Set<AdmissionScore> excelToGetAdmissionScore(InputStream inputStream) {
        Set<AdmissionScore> admissionScoreSet = new HashSet<>();
        Workbook workbook = null;
        try {
            // 根据传递过来的文件输入流创建一个workbook对象（对应Excel中的工作簿）
            workbook = WorkbookFactory.create(inputStream);
            // 创建完成，关闭输入流
            inputStream.close();
            //获取工作表对象，即第一个工作表 （工作簿里面有很多张工作表，这里取第一张工作表）
            Sheet sheet = workbook.getSheetAt(0);
            //获取工作表的总行数
            System.out.println(sheet.getLastRowNum());
            int rowLength = sheet.getLastRowNum() + 1;
//            System.out.println("总行数有多少行" + rowLength);
            //获取工作表第一行数据
            Row row = sheet.getRow(0);
            //获取工作表总列数的长度
            int colLength = row.getLastCellNum();
//            System.out.println("总列数有多少列" + colLength);
            // 创建一个单元格对象
            Cell cell = null;

            // 双重循环 因为一个单元格由行和列的索引下表构成
            for (int a = 1; a < rowLength; a++) {
                // 创建一个学生实体类对象
                AdmissionScore admissionScore = new AdmissionScore();
                for (int b = 0; b < colLength; b++) {
                    // 分别取出第 a行 b列的单元格数据
                    cell = sheet.getRow(a).getCell(b);
                    // 设置单元格的类型是String类型
                    cell.setCellType(CellType.STRING);
                    // 获取单元格的数据
                    String stringCellValue = cell.getStringCellValue();
                    System.out.println(stringCellValue);
                    // System.out.println(cell.getStringCellValue());
                    // 通过列来进行判断要赋值的属性
                    switch (b){
                        case 0:
                            admissionScore.setProvince_name(stringCellValue);
                            break;
                        case 1:
                            admissionScore.setSchool_name(stringCellValue);
                            break;
                        case 2:
                            admissionScore.setMajor(stringCellValue);
                            break;
                        case 3:
                            admissionScore.setKind(stringCellValue);
                            break;
                        case 4:
                            admissionScore.setYear(Integer.parseInt(stringCellValue));
                            break;
                        case 5:
                            admissionScore.setNum(Integer.parseInt(stringCellValue));
                            break;
                        case 6:
                            admissionScore.setHigh_score(Integer.parseInt(stringCellValue));
                            break;
                        case 7:
                            admissionScore.setLeast_score(Integer.parseInt(stringCellValue));
                            break;
                        case 8:
                            admissionScore.setAvg_score(Double.valueOf(stringCellValue).intValue());
                            break;
                        case 9:
                            admissionScore.setLeast_rank(Integer.parseInt(stringCellValue));
                    }
                }
                admissionScoreSet.add(admissionScore);
            }
        } catch (Exception e) {
            return null;
        }
        return admissionScoreSet;
    }
}

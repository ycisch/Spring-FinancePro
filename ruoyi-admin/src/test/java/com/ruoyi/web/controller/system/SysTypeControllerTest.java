package com.ruoyi.web.controller.system;


import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysFinance;
import com.ruoyi.system.domain.SysFinanceRecord;
import com.ruoyi.system.service.ISysFinanceRecordService;
import com.ruoyi.system.service.ISysFinanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class SysTypeControllerTest {

    @Autowired
    ISysFinanceService service;

    @Autowired
    ISysFinanceRecordService iSysFinanceRecordService;

    @Autowired
    SysFinanceController sysFinanceController;

    @Test
    void export(){

    }

    @Test
    void add() {
        String filePath = "E:\\记账本10.30.xlsx";
        String sheetName = "他人欠款账目";
        List<ReadExcel.Info> infos = ReadExcel.readExcel(filePath, sheetName);
        int year = 0;
        List<SysFinance> list = new ArrayList<>();
        HashMap<String, LocalDate> hashMap = new HashMap<>();
        HashMap<String, String> hashMap1 = new HashMap<>();
        for (ReadExcel.Info info : infos) {
            SysFinance finance = new SysFinance();
            finance.setFinanceDec(info.getName());
            String regex = "(\\d{1,2}月\\d{1,2}日)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(info.getName());
            if (info.getName().contains("年")) {
                String regex1 = "(\\d{4})年";
                Pattern pattern1 = Pattern.compile(regex1);
                Matcher matcher1 = pattern1.matcher(info.getName());

                // 查找并打印年份
                if (matcher1.find()) {
                    String yearStr = matcher1.group(1);
                    year = Integer.parseInt(yearStr);
                    System.out.println("年份：" + year);
                }
            }
            int x = 0;
            // 查找并转换日期
            LocalDate date2 = LocalDate.now();
            LocalDate date1 = LocalDate.now();
            while (matcher.find()) {
                if (x == 0) {
                    String dateStr = matcher.group();
                    // 去掉 "月" 和 "日" 字符
                    dateStr = dateStr.replace("月", ",").replace("日", "");
                    // 拆分月份和日期
                    String[] dateParts = dateStr.split(",");
                    int month = Integer.parseInt(dateParts[0]);
                    int day = Integer.parseInt(dateParts[1]);
                    // 构建日期对象
                    LocalDate date = LocalDate.of(year, month, day);
                    finance.setFinanceCreate(Date.valueOf(date));
                    date1 = date;
                }

                if (x == 1) {
                    String dateStr = matcher.group();
                    // 去掉 "月" 和 "日" 字符
                    dateStr = dateStr.replace("月", ",").replace("日", "");
                    // 拆分月份和日期
                    String[] dateParts = dateStr.split(",");
                    int month = Integer.parseInt(dateParts[0]);
                    int day = Integer.parseInt(dateParts[1]);
                    // 构建日期对象
                    LocalDate date = LocalDate.of(year, month, day);
//                    finance.setFinanceCreate(Date.valueOf(date));
                    date2 = date;
                    // 格式化日期为 "yyyy/M/d" 格式
                }
                x++;
            }
//            if (info.getCount().contains("-")) {
////                System.out.println(info.getCount());
//                hashMap.put(info.getCount(), date1);
//                hashMap1.put(info.getCount(), info.getName());
//                continue;
//            }
//            if (info.getName().contains("应付")) {
//                if (info.getType()) {
//                    finance.setFinanceType(20);
//                    finance.setFinanceFlag("0");
//                    finance.setFinanceCreate(Date.valueOf(date1));
//                    finance.setFinanceExpenditure(new BigDecimal(info.getCount()));
//                } else {
//                    finance.setFinanceType(20);
//                    finance.setFinanceFlag("1");
//                    finance.setFinanceCreate(Date.valueOf(date1));
//                    finance.setFinanceExpenditure(new BigDecimal(info.getCount()));
//                }
//                finance.setFinanceInfo(info.getCount());
//                list.add(finance);
//            } else if (info.getName().contains("已付")) {
//
//            }

            if(info.getCount().contains("-")){
                finance.setFinanceType(22);
                finance.setFinanceFlag("0");
                finance.setFinanceCreate(Date.valueOf(date1));
                finance.setFinanceExpendTime(Date.valueOf(date1));
                finance.setFinanceIncome(new BigDecimal(info.getCount().replace("-","")));
            } else{
                finance.setFinanceType(22);
                finance.setFinanceFlag("1");
                finance.setFinanceCreate(Date.valueOf(date1));
                finance.setFinanceExpenditure(new BigDecimal(info.getCount()));
            }
            list.add(finance);
        }

        for (int i = 0; i < list.size(); i++) {
            SysFinance sysFinance = list.get(i);
//            String count = "-" + sysFinance.getFinanceInfo();
//            System.out.println(count);
//            if (hashMap.containsKey(count)) {
//                // 格式化日期为 "yyyy/M/d" 格式
//                String formattedDate = hashMap.get(count).format(DateTimeFormatter.ofPattern("yyyy/M/d"));
//                System.out.println("转换后的日期：" + formattedDate);
//                sysFinance.setFinanceExpendTime(Date.valueOf(hashMap.get(count)));
//                sysFinance.setFinanceInfo(hashMap1.get(count));
//                System.out.println(sysFinance.getFinanceInfo() + "\n"
//                        + sysFinance.getFinanceDec() + "\n"
//                        + hashMap1.get(count));
//            }
//            System.out.println(sysFinance.getFinanceInfo()+"\n" + sysFinance.getFinanceDec());
            service.insertSysFinance(sysFinance);

//            SysFinance sysFinance1 = new SysFinance();
//            sysFinance1.setFinanceExpenditure(new BigDecimal(sysFinance.getFinanceInfo()));
//            List<SysFinance> list1 = service.selectSysFinanceList(sysFinance);
//            for (int j = 0 ; j < list1.size(); j++){
//                System.out.println(list1.get(j).toString());
//            }

//            service.insertSysFinance(sysFinance);

        }
    }

    @Test
    void find(){
        SysFinance sysFinance = new SysFinance();
        sysFinance.setDeleted("0");
        BigDecimal result = BigDecimal.valueOf(0);
        List<SysFinance> list = service.selectSysFinanceList(sysFinance);
        for(int i = 0; i < list.size(); i++){
            SysFinance sysFinance1 = list.get(i);
            if(sysFinance1.getFinanceFlag().equals("1") && sysFinance1.getFinanceType() == 20){
                BigDecimal financeExpenditure = sysFinance1.getFinanceExpenditure();
                result = result.add(financeExpenditure);
            }
        }
        System.out.println("总收入 " + result);
    }


    @Test
    void insertRecord(){
        SysFinance sysFinance = new SysFinance();
        sysFinance.setDeleted("0");
        BigDecimal result = BigDecimal.valueOf(0);
        List<SysFinance> list = service.selectSysFinanceList(sysFinance);
        for(int i = 0; i < list.size(); i++){
            SysFinance sysFinance1 = list.get(i);
            SysFinanceRecord sysFinanceRecord = new SysFinanceRecord();
            sysFinanceRecord.setFinanceIds(sysFinance1.getFinanceId());
            sysFinanceRecord.setRecordTime(sysFinance1.getFinanceExpendTime());
            sysFinanceRecord.setRecordMoney(sysFinance1.getFinanceExpenditure());
            iSysFinanceRecordService.insertSysFinanceRecord(sysFinanceRecord);
        }
    }
}
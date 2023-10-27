//package com.ruoyi.web.controller.system;
//
//import com.ruoyi.system.domain.SysFinance;
//
//
//import java.util.List;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SysFinanceControllerTest {
//
//
//    @Test
//    void add() {
//        String filePath = "E:\\记账本10.21.xlsx";
//        String sheetName = "3号收其他核桃仁合计";
//        List<ReadExcel.Info> infos = ReadExcel.readExcel(filePath, sheetName);
//        for (ReadExcel.Info info : infos) {
//            SysFinance finance = new SysFinance();
//            finance.setFinanceDec(info.getName());
//            if(info.getName().contains("应付")){
//
//            }else if(info.getName().contains("已付")){
//
//            }
//
//        }
//    }
//}
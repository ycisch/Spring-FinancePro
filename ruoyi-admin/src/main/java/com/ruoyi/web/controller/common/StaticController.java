package com.ruoyi.web.controller.common;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.SysFinance;
import com.ruoyi.system.domain.SysFinanceRecord;
import com.ruoyi.system.domain.SysStatic;
import com.ruoyi.system.domain.SysType;
import com.ruoyi.system.service.ISysFinanceRecordService;
import com.ruoyi.system.service.ISysFinanceService;
import com.ruoyi.system.service.ISysTypeService;
import javafx.scene.control.Tab;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 请求统计数据
 *
 * @author yinchen
 */
@RestController
@RequestMapping("/system/static")
public class StaticController extends BaseController {


    @Autowired
    ISysFinanceRecordService sysFinanceRecordService;

    @Autowired
    ISysFinanceService sysFinanceService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    ISysTypeService sysTypeService;


    @GetMapping("/getTableInfo")
    public TableDataInfo getTableInfo(SysFinance sysFinance){
        startPage();
        List<SysFinance> list = sysFinanceService.selectSysFinanceListOrderByTime();
        return getDataTable(list);
    }


    /**
     * 获取统计信息 柱状图显示
     */
    @GetMapping("/getStaticInfoList")
    public HashMap<String, Object> getStaticInfoList(@RequestParam String type) {
        HashMap<String, Object> result = new HashMap<>();
        List<BigDecimal> arrayMoney = new ArrayList<>();
        List<String> arrayName = new ArrayList<>();

        List<BigDecimal> arrayIncomeMoney = new ArrayList<>();
        List<String> arrayIncomeNme = new ArrayList<>();
        List<HashMap<String, String>> list = new ArrayList<>();

        List<SysStatic> sysStaticList = sysFinanceService.selectSysFinanceMoney();
        for (int i = 0; i < sysStaticList.size(); i++) {

            //获取当前表格对应的

            HashMap<String, String> hashMap = new HashMap<>();
            SysStatic sysStatic = sysStaticList.get(i);
            if ("expend".equals(type) && sysStatic.getExpendMoney() != null && sysStatic.getExpendMoney().compareTo(new BigDecimal(0)) != 0) {
                arrayMoney.add(sysStatic.getExpendMoney());
                arrayName.add(sysStatic.getStaticName());
                hashMap.put("value", String.valueOf(sysStatic.getExpendMoney()));
                hashMap.put("name", sysStatic.getStaticName());
                list.add(hashMap);
            }
            if ("income".equals(type) && sysStatic.getIncomeMoney() != null && sysStatic.getIncomeMoney().compareTo(new BigDecimal(0)) != 0) {
                arrayMoney.add(sysStatic.getIncomeMoney());
                arrayName.add(sysStatic.getStaticName());
                hashMap.put("value", String.valueOf(sysStatic.getIncomeMoney()));
                hashMap.put("name", sysStatic.getStaticName());
                list.add(hashMap);
            }
        }

        result.put("arrayMoney", arrayMoney);
        result.put("arrayName", arrayName);
        result.put("pieInfo", list);
        redisCache.setCacheObject(type, result);
        return result;
    }


    /**
     * 获取全部信息
     *
     * @return
     */
    @GetMapping("getLineInfo")
    public HashMap<String, Object> getLineInfo(SysFinance sysFinance) {
        sysFinance.setFinanceType(21);
        sysFinance.setDeleted("0");
        HashMap<String, Object> result = new HashMap<>();
//        System.out.println(sysFinance.getFinanceCreate() + "  " + sysFinance.getFinanceExpendTime());
        List<SysFinance> list = sysFinanceService.selectSysFinanceList(sysFinance);
        List<BigDecimal> numInfo = new ArrayList<>();
        List<String> dateInfo = new ArrayList<>();
        BigDecimal bigDecimal = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            SysFinance sysFinance1 = list.get(i);
            if (sysFinance1.getFinanceExpenditure() != null) {
                bigDecimal = bigDecimal.add(sysFinance1.getFinanceExpenditure());
            }
            if (sysFinance1.getFinanceIncome() != null) {
                bigDecimal = bigDecimal.subtract(sysFinance1.getFinanceIncome());
            }
            if (sysFinance1.getFinanceCreate().compareTo(sysFinance.getFinanceCreate()) >= 0
                    && sysFinance1.getFinanceCreate().compareTo(sysFinance.getFinanceExpendTime()) <= 0) {
                dateInfo.add(DateUtils.parseDateToStr("yyyy-MM-dd", sysFinance1.getFinanceCreate()));
                numInfo.add(bigDecimal);
            }
        }
        result.put("dateInfo", dateInfo);
        result.put("numInfo", numInfo);
        return result;
    }

    @GetMapping("getLineInfoList")
    public HashMap<String, Object> getLineInfoList(SysFinance sysFinance) {
        sysFinance.setFinanceType(21);
        sysFinance.setDeleted("0");
        HashMap<String, Object> result = new HashMap<>();
//        System.out.println(sysFinance.getFinanceCreate() + "  " + sysFinance.getFinanceExpendTime());
        List<SysFinance> list = sysFinanceService.selectSysFinanceList(sysFinance);
        List<BigDecimal> expendInfo = new ArrayList<>();
        List<BigDecimal> incomeInfo = new ArrayList<>();
        List<String> dateInfo = new ArrayList<>();
        BigDecimal expendMoney = new BigDecimal(0);
        BigDecimal incomeMoney = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            SysFinance sysFinance1 = list.get(i);
            BigDecimal bigDecimal = new BigDecimal(0);
            BigDecimal bigDecimal1 = new BigDecimal(0);
            if (sysFinance1.getFinanceExpenditure() != null) {
                bigDecimal = sysFinance1.getFinanceExpenditure();
                expendMoney = expendMoney.add(bigDecimal);
            }
            if (sysFinance1.getFinanceIncome() != null) {
                bigDecimal1 = sysFinance1.getFinanceIncome();
                incomeMoney = incomeMoney.add(bigDecimal1);
            }
            if (sysFinance1.getFinanceCreate().compareTo(sysFinance.getFinanceCreate()) >= 0
                    && sysFinance1.getFinanceCreate().compareTo(sysFinance.getFinanceExpendTime()) <= 0) {
                dateInfo.add(DateUtils.parseDateToStr("yyyy-MM-dd", sysFinance1.getFinanceCreate()));
                expendInfo.add(bigDecimal);
                incomeInfo.add(bigDecimal1);
            }
        }
        List<Object> pieInfo = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("value", String.valueOf(expendMoney));
        hashMap.put("name", "出款");
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("value", String.valueOf(incomeMoney));
        hashMap1.put("name", "入款");
        pieInfo.add(hashMap);
        pieInfo.add(hashMap1);
        result.put("pieInfo",pieInfo);
        result.put("dateInfo", dateInfo);
        result.put("expendInfo", expendInfo);
        result.put("incomeInfo", incomeInfo);
        return result;
    }

    /**
     * 获取首页上方数值
     *
     * @return
     */
    @GetMapping("getStaticInfo")
    public HashMap<String, String> getStaticInfo() {
        BigDecimal incomeAll = new BigDecimal(0);
        BigDecimal expendAll = new BigDecimal(0);
        BigDecimal incomeAllInfo = new BigDecimal(0);
        BigDecimal expendAllInfo = new BigDecimal(0);
        List<SysStatic> sysStaticList = sysFinanceService.selectSysFinanceMoney();
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < sysStaticList.size(); i++) {
            SysStatic sysStatic = sysStaticList.get(i);
            if (sysStatic.getTypeId() == 21) {
                if (sysStatic.getExpendMoney() != null) {
                    incomeAllInfo = incomeAllInfo.add(sysStatic.getExpendMoney());
                }
                if (sysStatic.getIncomeMoney() != null) {
                    incomeAllInfo = incomeAllInfo.subtract(sysStatic.getIncomeMoney());
                }
            } else {
                if (sysStatic.getExpendMoney() != null && sysStatic.getExpendMoney().compareTo(new BigDecimal(0)) != 0) {
                    expendAll = expendAll.add(sysStatic.getExpendMoney());
                }
                if (sysStatic.getIncomeMoney() != null && sysStatic.getIncomeMoney().compareTo(new BigDecimal(0)) != 0) {
                    incomeAll = incomeAll.add(sysStatic.getIncomeMoney());
                }
            }
        }
        BigDecimal bigDecimal = expendAll.subtract(incomeAll);

        //获取总收入与总支出
        hashMap.put("expendAll", String.valueOf(expendAll));
        hashMap.put("incomeAll", String.valueOf(incomeAll));
        //获取山东总收入
        hashMap.put("incomeAllInfo", String.valueOf(incomeAllInfo));
        //获取新疆总支出
        hashMap.put("expendAllInfo", String.valueOf(expendAllInfo));
        redisCache.setCacheObject("static_info", hashMap);

        return hashMap;
    }
}
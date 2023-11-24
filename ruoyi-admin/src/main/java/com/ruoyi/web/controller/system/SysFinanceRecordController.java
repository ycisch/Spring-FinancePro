package com.ruoyi.web.controller.system;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysFinance;
import com.ruoyi.system.service.ISysFinanceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysFinanceRecord;
import com.ruoyi.system.service.ISysFinanceRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 结账记录格Controller
 *
 * @author ruoyi
 * @date 2023-10-26
 */
@RestController
@RequestMapping("/system/record")
public class SysFinanceRecordController extends BaseController {
    @Autowired
    private ISysFinanceRecordService sysFinanceRecordService;

    @Autowired
    private ISysFinanceService sysFinanceService;

    /**
     * 查询结账记录格列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFinanceRecord sysFinanceRecord) {
        startPage();
        List<SysFinanceRecord> list = sysFinanceRecordService.selectSysFinanceRecordList(sysFinanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出结账记录格列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:export')")
    @Log(title = "结账记录格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFinanceRecord sysFinanceRecord) {
        List<SysFinanceRecord> list = sysFinanceRecordService.selectSysFinanceRecordList(sysFinanceRecord);
        ExcelUtil<SysFinanceRecord> util = new ExcelUtil<SysFinanceRecord>(SysFinanceRecord.class);
        util.exportExcel(response, list, "结账记录格数据");
    }

    /**
     * 获取结账记录格详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sysFinanceRecordService.selectSysFinanceRecordById(id));
    }

    /**
     * 新增结账记录格
     */
    @PreAuthorize("@ss.hasPermi('system:record:add')")
    @Log(title = "结账记录格", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody SysFinanceRecord sysFinanceRecord) {
        sysFinanceRecord.setOperator(SecurityUtils.getUsername());
        sysFinanceRecordService.insertSysFinanceRecord(sysFinanceRecord);
        try {
            checkRecord(sysFinanceRecord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toAjax(true);
    }

    /**
     * 修改结账记录格
     */
    @PreAuthorize("@ss.hasPermi('system:record:edit')")
    @Log(title = "结账记录格", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody SysFinanceRecord sysFinanceRecord) {
        sysFinanceRecordService.updateSysFinanceRecord(sysFinanceRecord);
        try {
            checkRecord(sysFinanceRecord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toAjax(true);
    }

    /**
     * 删除结账记录格
     */
    @PreAuthorize("@ss.hasPermi('system:record:remove')")
    @Log(title = "结账记录格", businessType = BusinessType.DELETE)
    @GetMapping("deleteRecord")
    @Transactional
    public AjaxResult remove(@RequestParam("ids") Long[] ids, @RequestParam("financeId") Long financeId) {
        sysFinanceRecordService.deleteSysFinanceRecordByIds(ids);
        SysFinanceRecord sysFinanceRecord = new SysFinanceRecord();
        sysFinanceRecord.setFinanceIds(financeId);
        try {
            checkRecord(sysFinanceRecord);
        } catch (Exception e) {
            throw new RuntimeException("金额输入有误(结账金额与总金额不符合)，请重新输入");
        }
        return toAjax(true);
    }

    public void checkRecord(SysFinanceRecord sysFinanceRecord) throws Exception {

        SysFinanceRecord sysFinanceRecord1 = new SysFinanceRecord();
        Long financeIds = sysFinanceRecord.getFinanceIds();
        SysFinance sysFinance = sysFinanceService.selectSysFinanceByFinanceId(financeIds);
        sysFinanceRecord1.setFinanceIds(sysFinanceRecord.getFinanceIds());
        //查看当前结账金额是否等于出账金额
        List<SysFinanceRecord> sysFinanceRecords = sysFinanceRecordService.selectSysFinanceRecordList(sysFinanceRecord1);
        BigDecimal bigDecimal = new BigDecimal(0);
        for (int i = 0; i < sysFinanceRecords.size(); i++) {
            bigDecimal = bigDecimal.add(sysFinanceRecords.get(i).getRecordMoney());
        }
        if (sysFinance.getFinanceExpenditure() != null) {
            if (sysFinance.getFinanceExpenditure().compareTo(bigDecimal) == 0) {
                sysFinance.setFinanceFlag("0");
                sysFinance.setFinanceExpendTime(sysFinanceRecord.getRecordTime());
                sysFinanceService.updateSysFinance(sysFinance);
            } else if (sysFinance.getFinanceExpenditure().compareTo(bigDecimal) > 0) {
                //将flag和时间重置
                sysFinance.setFinanceFlag("1");
                sysFinance.setFinanceExpendTime(null);
                sysFinanceService.updateSysFinanceFlagInfo(sysFinance);
            } else if (sysFinance.getFinanceExpenditure().compareTo(bigDecimal) < 0) {
                //还款错误 请仔细检查
                throw new RuntimeException("返回错误信息");
            }
        } else if (sysFinance.getFinanceIncome() != null) {
            if (sysFinance.getFinanceIncome().compareTo(bigDecimal) == 0) {
                sysFinance.setFinanceFlag("0");
                sysFinance.setFinanceExpendTime(sysFinanceRecord.getRecordTime());
                sysFinanceService.updateSysFinance(sysFinance);
            } else if (sysFinance.getFinanceIncome().compareTo(bigDecimal) > 0) {
                //将flag和时间重置
                sysFinance.setFinanceFlag("1");
                sysFinance.setFinanceExpendTime(null);
                sysFinanceService.updateSysFinanceFlagInfo(sysFinance);
            } else if (sysFinance.getFinanceIncome().compareTo(bigDecimal) < 0) {
                //还款错误 请仔细检查
                throw new RuntimeException("返回错误信息");
            }
        }
    }

    @GetMapping(value = "getFinanceRecordInfo")
    public HashMap<String, Object> getFinanceRecordInfo(SysFinanceRecord sysFinanceRecord) {
        SysFinance sysFinance1 = sysFinanceService.selectSysFinanceByFinanceId(sysFinanceRecord.getFinanceIds());
        List<SysFinanceRecord> sysFinanceRecords = sysFinanceRecordService.selectSysFinanceRecordList(sysFinanceRecord);
        if(sysFinanceRecords !=null && sysFinanceRecords.size() != 0 ){
            // 按照recordTime进行降序排序
            List<SysFinanceRecord> sortedRecords = sysFinanceRecords.stream()
                    .sorted(Comparator.comparing(SysFinanceRecord::getRecordTime, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
            sysFinanceRecords = sortedRecords;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sysFinance", sysFinance1);
        hashMap.put("recordList", sysFinanceRecords);
        return hashMap;
    }
}

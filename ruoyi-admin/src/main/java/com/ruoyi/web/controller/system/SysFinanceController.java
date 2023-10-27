package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysFinance;
import com.ruoyi.system.service.ISysFinanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 财务格
 * - 主要用于存储多个格之间的信息Controller
 *
 * @author ruoyi
 * @date 2023-09-26
 */
@RestController
@RequestMapping("/system/finance")
public class SysFinanceController extends BaseController {
    @Autowired
    private ISysFinanceService sysFinanceService;

    /**
     * 查询财务格
     * - 主要用于存储多个格之间的信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:finance:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFinance sysFinance) {
        startPage();
        sysFinance.setDeleted("0");
        List<SysFinance> list = sysFinanceService.selectSysFinanceList(sysFinance);
        return getDataTable(list);
    }

    /**
     * 导出财务格
     * - 主要用于存储多个格之间的信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:finance:export')")
    @Log(title = "财务格 - 主要用于存储多个格之间的信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFinance sysFinance) {
        List<SysFinance> list = sysFinanceService.selectSysFinanceList(sysFinance);
        ExcelUtil<SysFinance> util = new ExcelUtil<SysFinance>(SysFinance.class);
        util.exportExcel(response, list, "财务格 - 主要用于存储多个格之间的信息数据");
    }

    /**
     * 获取财务格
     * - 主要用于存储多个格之间的信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:finance:query')")
    @GetMapping(value = "/{financeId}")
    public AjaxResult getInfo(@PathVariable("financeId") Long financeId) {
        return success(sysFinanceService.selectSysFinanceByFinanceId(financeId));
    }

    /**
     * 新增财务格
     * - 主要用于存储多个格之间的信息
     */
    @PreAuthorize("@ss.hasPermi('system:finance:add')")
    @Log(title = "财务格 - 主要用于存储多个格之间的信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFinance sysFinance) {
        //获取现在创建时间
        if(sysFinance.getFinanceFlag() != null && "0".equals(sysFinance.getFinanceFlag())){
            sysFinance.setFinanceExpendTime(sysFinance.getFinanceCreate());
        }
        sysFinance.setFinanceUpdate(sysFinance.getFinanceCreate());
        return toAjax(sysFinanceService.insertSysFinance(sysFinance));
    }

    /**
     * 修改财务格
     * - 主要用于存储多个格之间的信息
     */
    @PreAuthorize("@ss.hasPermi('system:finance:edit')")
    @Log(title = "财务格 - 主要用于存储多个格之间的信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFinance sysFinance) {
        sysFinance.setFinanceUpdate(new Date());
        return toAjax(sysFinanceService.updateSysFinance(sysFinance));
    }

    /**
     * 删除财务格
     * - 主要用于存储多个格之间的信息
     */
    @PreAuthorize("@ss.hasPermi('system:finance:remove')")
    @Log(title = "财务格 - 主要用于存储多个格之间的信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{financeIds}")
    public AjaxResult remove(@PathVariable Long[] financeIds) {
        return toAjax(sysFinanceService.deleteSysFinanceByFinanceIds(financeIds));
    }
}
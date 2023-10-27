package com.ruoyi.system.controller;

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
public class SysFinanceRecordController extends BaseController
{
    @Autowired
    private ISysFinanceRecordService sysFinanceRecordService;

    /**
     * 查询结账记录格列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFinanceRecord sysFinanceRecord)
    {
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
    public void export(HttpServletResponse response, SysFinanceRecord sysFinanceRecord)
    {
        List<SysFinanceRecord> list = sysFinanceRecordService.selectSysFinanceRecordList(sysFinanceRecord);
        ExcelUtil<SysFinanceRecord> util = new ExcelUtil<SysFinanceRecord>(SysFinanceRecord.class);
        util.exportExcel(response, list, "结账记录格数据");
    }

    /**
     * 获取结账记录格详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysFinanceRecordService.selectSysFinanceRecordById(id));
    }

    /**
     * 新增结账记录格
     */
    @PreAuthorize("@ss.hasPermi('system:record:add')")
    @Log(title = "结账记录格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFinanceRecord sysFinanceRecord)
    {
        return toAjax(sysFinanceRecordService.insertSysFinanceRecord(sysFinanceRecord));
    }

    /**
     * 修改结账记录格
     */
    @PreAuthorize("@ss.hasPermi('system:record:edit')")
    @Log(title = "结账记录格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFinanceRecord sysFinanceRecord)
    {
        return toAjax(sysFinanceRecordService.updateSysFinanceRecord(sysFinanceRecord));
    }

    /**
     * 删除结账记录格
     */
    @PreAuthorize("@ss.hasPermi('system:record:remove')")
    @Log(title = "结账记录格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysFinanceRecordService.deleteSysFinanceRecordByIds(ids));
    }
}

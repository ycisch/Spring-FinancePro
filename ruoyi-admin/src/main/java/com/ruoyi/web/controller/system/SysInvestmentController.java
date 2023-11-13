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
import com.ruoyi.system.domain.SysInvestment;
import com.ruoyi.system.service.ISysInvestmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投资信息Controller
 *
 * @author ruoyi
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/system/investment")
public class SysInvestmentController extends BaseController
{
    @Autowired
    private ISysInvestmentService sysInvestmentService;

    /**
     * 查询投资信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:investment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysInvestment sysInvestment)
    {
        startPage();
        List<SysInvestment> list = sysInvestmentService.selectSysInvestmentList(sysInvestment);
        return getDataTable(list);
    }

    /**
     * 导出投资信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:investment:export')")
    @Log(title = "投资信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysInvestment sysInvestment)
    {
        List<SysInvestment> list = sysInvestmentService.selectSysInvestmentList(sysInvestment);
        ExcelUtil<SysInvestment> util = new ExcelUtil<SysInvestment>(SysInvestment.class);
        util.exportExcel(response, list, "投资信息数据");
    }

    /**
     * 获取投资信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:investment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysInvestmentService.selectSysInvestmentById(id));
    }

    /**
     * 新增投资信息
     */
    @PreAuthorize("@ss.hasPermi('system:investment:add')")
    @Log(title = "投资信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysInvestment sysInvestment)
    {
        return toAjax(sysInvestmentService.insertSysInvestment(sysInvestment));
    }

    /**
     * 修改投资信息
     */
    @PreAuthorize("@ss.hasPermi('system:investment:edit')")
    @Log(title = "投资信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysInvestment sysInvestment)
    {
        return toAjax(sysInvestmentService.updateSysInvestment(sysInvestment));
    }

    /**
     * 删除投资信息
     */
    @PreAuthorize("@ss.hasPermi('system:investment:remove')")
    @Log(title = "投资信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysInvestmentService.deleteSysInvestmentByIds(ids));
    }
}

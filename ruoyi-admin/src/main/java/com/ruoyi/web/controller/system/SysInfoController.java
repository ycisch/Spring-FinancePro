package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.ruoyi.system.domain.SysInfo;
import com.ruoyi.system.service.ISysInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 类型的其他字段Controller
 *
 * @author ruoyi
 * @date 2023-10-30
 */
@RestController
@RequestMapping("/system/info")
public class SysInfoController extends BaseController
{
    @Autowired
    private ISysInfoService sysInfoService;

    /**
     * 查询类型的其他字段列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysInfo sysInfo)
    {
        startPage();
        List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
        return getDataTable(list);
    }

    /**
     * 导出类型的其他字段列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "类型的其他字段", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysInfo sysInfo)
    {
        List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
        ExcelUtil<SysInfo> util = new ExcelUtil<SysInfo>(SysInfo.class);
        util.exportExcel(response, list, "类型的其他字段数据");
    }

    /**
     * 获取类型的其他字段详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId)
    {
        return success(sysInfoService.selectSysInfoByInfoId(infoId));
    }

    /**
     * 新增类型的其他字段
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "类型的其他字段", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.insertSysInfo(sysInfo));
    }

    /**
     * 修改类型的其他字段
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "类型的其他字段", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.updateSysInfo(sysInfo));
    }

    /**
     * 删除类型的其他字段
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "类型的其他字段", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(sysInfoService.deleteSysInfoByInfoIds(infoIds));
    }

    @GetMapping("/getFinanceBasicInfo")
    public TableDataInfo getFinanceBasicInfo(@Valid SysInfo sysInfo) {
        return getDataTable(sysInfoService.selectSysInfoList(sysInfo));
    }
}

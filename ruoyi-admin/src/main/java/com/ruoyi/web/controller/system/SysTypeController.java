package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.redis.RedisCache;
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
import com.ruoyi.system.domain.SysType;
import com.ruoyi.system.service.ISysTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 格信息用于保存多个格 每个格都有自己的名称 类似于ExcelController
 *
 * @author ruoyi
 * @date 2023-10-07
 */
@RestController
@RequestMapping("/system/type")
public class SysTypeController extends BaseController
{
    @Autowired
    private ISysTypeService sysTypeService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询格信息用于保存多个格 每个格都有自己的名称 类似于Excel列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysType sysType)
    {
        startPage();
        sysType.setDeleted("0");
        List<SysType> list = sysTypeService.selectSysTypeList(sysType);
        return getDataTable(list);
    }

    /**
     * 导出格信息用于保存多个格 每个格都有自己的名称 类似于Excel列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:export')")
    @Log(title = "格信息用于保存多个格 每个格都有自己的名称 类似于Excel", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysType sysType)
    {
        sysType.setDeleted("0");
        List<SysType> list = sysTypeService.selectSysTypeList(sysType);
        ExcelUtil<SysType> util = new ExcelUtil<SysType>(SysType.class);
        util.exportExcel(response, list, "格信息用于保存多个格 每个格都有自己的名称 类似于Excel数据");
    }

    /**
     * 获取格信息用于保存多个格 每个格都有自己的名称 类似于Excel详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return success(sysTypeService.selectSysTypeByTypeId(typeId));
    }

    /**
     * 新增格信息用于保存多个格 每个格都有自己的名称 类似于Excel
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @Log(title = "格信息用于保存多个格 每个格都有自己的名称 类似于Excel", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysType sysType)
    {
        sysType.setTypeTime(new Date());
        sysTypeService.insertSysType(sysType);
        saveTypeInfoToRedis();
        return toAjax(true);
    }

    /**
     * 修改格信息用于保存多个格 每个格都有自己的名称 类似于Excel
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "格信息用于保存多个格 每个格都有自己的名称 类似于Excel", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysType sysType)
    {
        sysTypeService.updateSysType(sysType);
        saveTypeInfoToRedis();
        return toAjax(true);
    }

    /**
     * 删除格信息用于保存多个格 每个格都有自己的名称 类似于Excel
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "格信息用于保存多个格 每个格都有自己的名称 类似于Excel", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        sysTypeService.deleteSysTypeByTypeIds(typeIds);
        saveTypeInfoToRedis();
        return toAjax(true);
    }


    @DeleteMapping("/deleteSysTypeInfos/{typeIds}")
    public AjaxResult deleteSysTypeInfos(@PathVariable Long[] typeIds)
    {
        sysTypeService.deleteSysTypeInfos(typeIds);
        saveTypeInfoToRedis();
        return toAjax(true);
    }

    public void saveTypeInfoToRedis(){
        SysType sysType = new SysType();
        sysType.setDeleted("0");
        List<SysType> sysTypes = sysTypeService.selectSysTypeList(sysType);
        redisCache.setCacheObject("type_info",sysTypes);
    }
}

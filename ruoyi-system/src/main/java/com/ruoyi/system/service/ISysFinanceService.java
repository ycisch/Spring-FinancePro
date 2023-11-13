package com.ruoyi.system.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.SysFinance;
import com.ruoyi.system.domain.SysStatic;

/**
 * 财务格
- 主要用于存储多个格之间的信息Service接口
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
public interface ISysFinanceService 
{
    /**
     * 查询财务格
- 主要用于存储多个格之间的信息
     * 
     * @param financeId 财务格
- 主要用于存储多个格之间的信息主键
     * @return 财务格
- 主要用于存储多个格之间的信息
     */
    public SysFinance selectSysFinanceByFinanceId(Long financeId);

    /**
     * 查询财务格
- 主要用于存储多个格之间的信息列表
     * 
     * @param sysFinance 财务格
- 主要用于存储多个格之间的信息
     * @return 财务格
- 主要用于存储多个格之间的信息集合
     */
    public List<SysFinance> selectSysFinanceList(SysFinance sysFinance);



    public List<SysFinance> selectSysFinanceListContainTime(SysFinance sysFinance);

    /**
     * 获取金额信息
     * @param sysFinance
     * @return
     */
    public List<SysStatic> selectSysFinanceMoney();

    public List<SysFinance> selectSysFinanceListOrderByTime();

    /**
     * 新增财务格
- 主要用于存储多个格之间的信息
     * 
     * @param sysFinance 财务格
- 主要用于存储多个格之间的信息
     * @return 结果
     */
    public int insertSysFinance(SysFinance sysFinance);

    /**
     * 修改财务格
- 主要用于存储多个格之间的信息
     * 
     * @param sysFinance 财务格
- 主要用于存储多个格之间的信息
     * @return 结果
     */
    public int updateSysFinance(SysFinance sysFinance);

    /**
     * 只更新Flag和时间
     */
    public int updateSysFinanceFlagInfo(SysFinance sysFinance);

    /**
     * 批量删除财务格
- 主要用于存储多个格之间的信息
     * 
     * @param financeIds 需要删除的财务格
- 主要用于存储多个格之间的信息主键集合
     * @return 结果
     */
    public int deleteSysFinanceByFinanceIds(Long[] financeIds);

    /**
     * 删除财务格
- 主要用于存储多个格之间的信息信息
     * 
     * @param financeId 财务格
- 主要用于存储多个格之间的信息主键
     * @return 结果
     */
    public int deleteSysFinanceByFinanceId(Long financeId);
}

package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.SysStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysFinanceMapper;
import com.ruoyi.system.domain.SysFinance;
import com.ruoyi.system.service.ISysFinanceService;

/**
 * 财务格
- 主要用于存储多个格之间的信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
@Service
public class SysFinanceServiceImpl implements ISysFinanceService 
{
    @Autowired
    private SysFinanceMapper sysFinanceMapper;

    /**
     * 查询财务格
- 主要用于存储多个格之间的信息
     * 
     * @param financeId 财务格
- 主要用于存储多个格之间的信息主键
     * @return 财务格
- 主要用于存储多个格之间的信息
     */
    @Override
    public SysFinance selectSysFinanceByFinanceId(Long financeId)
    {
        return sysFinanceMapper.selectSysFinanceByFinanceId(financeId);
    }

    /**
     * 查询财务格
- 主要用于存储多个格之间的信息列表
     * 
     * @param sysFinance 财务格
- 主要用于存储多个格之间的信息
     * @return 财务格
- 主要用于存储多个格之间的信息
     */
    @Override
    public List<SysFinance> selectSysFinanceList(SysFinance sysFinance)
    {
        return sysFinanceMapper.selectSysFinanceList(sysFinance);
    }

    @Override
    public List<SysFinance> selectSysFinanceListContainTime(SysFinance sysFinance) {
        return sysFinanceMapper.selectSysFinanceListContainTime(sysFinance);
    }

    @Override
    public List<SysStatic> selectSysFinanceMoney() {
        return sysFinanceMapper.selectSysFinanceMoney();
    }

    @Override
    public List<SysFinance> selectSysFinanceListOrderByTime() {
        return sysFinanceMapper.selectSysFinanceListOrderByTime();
    }

    /**
     * 新增财务格
- 主要用于存储多个格之间的信息
     * 
     * @param sysFinance 财务格
- 主要用于存储多个格之间的信息
     * @return 结果
     */
    @Override
    public int insertSysFinance(SysFinance sysFinance)
    {
        return sysFinanceMapper.insertSysFinance(sysFinance);
    }

    /**
     * 修改财务格
- 主要用于存储多个格之间的信息
     * 
     * @param sysFinance 财务格
- 主要用于存储多个格之间的信息
     * @return 结果
     */
    @Override
    public int updateSysFinance(SysFinance sysFinance)
    {
        return sysFinanceMapper.updateSysFinance(sysFinance);
    }

    @Override
    public int updateSysFinanceFlagInfo(SysFinance sysFinance) {
        return sysFinanceMapper.updateSysFinanceFlagInfo(sysFinance);
    }

    /**
     * 批量删除财务格
- 主要用于存储多个格之间的信息
     * 
     * @param financeIds 需要删除的财务格
- 主要用于存储多个格之间的信息主键
     * @return 结果
     */
    @Override
    public int deleteSysFinanceByFinanceIds(Long[] financeIds)
    {
        return sysFinanceMapper.deleteSysFinanceByFinanceIds(financeIds);
    }

    /**
     * 删除财务格
- 主要用于存储多个格之间的信息信息
     * 
     * @param financeId 财务格
- 主要用于存储多个格之间的信息主键
     * @return 结果
     */
    @Override
    public int deleteSysFinanceByFinanceId(Long financeId)
    {
        return sysFinanceMapper.deleteSysFinanceByFinanceId(financeId);
    }

    @Override
    public List<SysFinance> getFinanceRecordInfo(Long financeId) {
        return sysFinanceMapper.getFinanceRecordInfo(financeId);
    }
}

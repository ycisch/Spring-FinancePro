package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysFinance;

/**
 * 财务格
- 主要用于存储多个格之间的信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
public interface SysFinanceMapper 
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
     * 删除财务格
- 主要用于存储多个格之间的信息
     * 
     * @param financeId 财务格
- 主要用于存储多个格之间的信息主键
     * @return 结果
     */
    public int deleteSysFinanceByFinanceId(Long financeId);

    /**
     * 批量删除财务格
- 主要用于存储多个格之间的信息
     * 
     * @param financeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFinanceByFinanceIds(Long[] financeIds);
}
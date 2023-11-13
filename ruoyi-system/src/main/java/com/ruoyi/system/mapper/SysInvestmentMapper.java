package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysInvestment;

/**
 * 投资信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-27
 */
public interface SysInvestmentMapper
{
    /**
     * 查询投资信息
     *
     * @param id 投资信息主键
     * @return 投资信息
     */
    public SysInvestment selectSysInvestmentById(Long id);

    /**
     * 查询投资信息列表
     *
     * @param sysInvestment 投资信息
     * @return 投资信息集合
     */
    public List<SysInvestment> selectSysInvestmentList(SysInvestment sysInvestment);

    /**
     * 新增投资信息
     *
     * @param sysInvestment 投资信息
     * @return 结果
     */
    public int insertSysInvestment(SysInvestment sysInvestment);

    /**
     * 修改投资信息
     *
     * @param sysInvestment 投资信息
     * @return 结果
     */
    public int updateSysInvestment(SysInvestment sysInvestment);

    /**
     * 删除投资信息
     *
     * @param id 投资信息主键
     * @return 结果
     */
    public int deleteSysInvestmentById(Long id);

    /**
     * 批量删除投资信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInvestmentByIds(Long[] ids);
}

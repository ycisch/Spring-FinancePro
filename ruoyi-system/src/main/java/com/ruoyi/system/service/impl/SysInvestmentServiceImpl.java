package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysInvestmentMapper;
import com.ruoyi.system.domain.SysInvestment;
import com.ruoyi.system.service.ISysInvestmentService;

/**
 * 投资信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-27
 */
@Service
public class SysInvestmentServiceImpl implements ISysInvestmentService
{
    @Autowired
    private SysInvestmentMapper sysInvestmentMapper;

    /**
     * 查询投资信息
     *
     * @param id 投资信息主键
     * @return 投资信息
     */
    @Override
    public SysInvestment selectSysInvestmentById(Long id)
    {
        return sysInvestmentMapper.selectSysInvestmentById(id);
    }

    /**
     * 查询投资信息列表
     *
     * @param sysInvestment 投资信息
     * @return 投资信息
     */
    @Override
    public List<SysInvestment> selectSysInvestmentList(SysInvestment sysInvestment)
    {
        return sysInvestmentMapper.selectSysInvestmentList(sysInvestment);
    }

    /**
     * 新增投资信息
     *
     * @param sysInvestment 投资信息
     * @return 结果
     */
    @Override
    public int insertSysInvestment(SysInvestment sysInvestment)
    {
        sysInvestment.setCreateTime(DateUtils.getNowDate());
        return sysInvestmentMapper.insertSysInvestment(sysInvestment);
    }

    /**
     * 修改投资信息
     *
     * @param sysInvestment 投资信息
     * @return 结果
     */
    @Override
    public int updateSysInvestment(SysInvestment sysInvestment)
    {
        return sysInvestmentMapper.updateSysInvestment(sysInvestment);
    }

    /**
     * 批量删除投资信息
     *
     * @param ids 需要删除的投资信息主键
     * @return 结果
     */
    @Override
    public int deleteSysInvestmentByIds(Long[] ids)
    {
        return sysInvestmentMapper.deleteSysInvestmentByIds(ids);
    }

    /**
     * 删除投资信息信息
     *
     * @param id 投资信息主键
     * @return 结果
     */
    @Override
    public int deleteSysInvestmentById(Long id)
    {
        return sysInvestmentMapper.deleteSysInvestmentById(id);
    }
}

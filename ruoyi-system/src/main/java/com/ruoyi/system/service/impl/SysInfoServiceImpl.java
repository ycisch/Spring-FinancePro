package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysInfoMapper;
import com.ruoyi.system.domain.SysInfo;
import com.ruoyi.system.service.ISysInfoService;

/**
 * 类型的其他字段Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-30
 */
@Service
public class SysInfoServiceImpl implements ISysInfoService
{
    @Autowired
    private SysInfoMapper sysInfoMapper;

    /**
     * 查询类型的其他字段
     *
     * @param infoId 类型的其他字段主键
     * @return 类型的其他字段
     */
    @Override
    public SysInfo selectSysInfoByInfoId(Long infoId)
    {
        return sysInfoMapper.selectSysInfoByInfoId(infoId);
    }

    /**
     * 查询类型的其他字段列表
     *
     * @param sysInfo 类型的其他字段
     * @return 类型的其他字段
     */
    @Override
    public List<SysInfo> selectSysInfoList(SysInfo sysInfo)
    {
        return sysInfoMapper.selectSysInfoList(sysInfo);
    }

    /**
     * 新增类型的其他字段
     *
     * @param sysInfo 类型的其他字段
     * @return 结果
     */
    @Override
    public int insertSysInfo(SysInfo sysInfo)
    {
        sysInfo.setCreateTime(DateUtils.getNowDate());
        return sysInfoMapper.insertSysInfo(sysInfo);
    }

    /**
     * 修改类型的其他字段
     *
     * @param sysInfo 类型的其他字段
     * @return 结果
     */
    @Override
    public int updateSysInfo(SysInfo sysInfo)
    {
        sysInfo.setUpdateTime(DateUtils.getNowDate());
        return sysInfoMapper.updateSysInfo(sysInfo);
    }

    /**
     * 批量删除类型的其他字段
     *
     * @param infoIds 需要删除的类型的其他字段主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoByInfoIds(Long[] infoIds)
    {
        return sysInfoMapper.deleteSysInfoByInfoIds(infoIds);
    }

    /**
     * 删除类型的其他字段信息
     *
     * @param infoId 类型的其他字段主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoByInfoId(Long infoId)
    {
        return sysInfoMapper.deleteSysInfoByInfoId(infoId);
    }
}

package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysInfo;

/**
 * 类型的其他字段Service接口
 *
 * @author ruoyi
 * @date 2023-10-30
 */
public interface ISysInfoService
{
    /**
     * 查询类型的其他字段
     *
     * @param infoId 类型的其他字段主键
     * @return 类型的其他字段
     */
    public SysInfo selectSysInfoByInfoId(Long infoId);

    /**
     * 查询类型的其他字段列表
     *
     * @param sysInfo 类型的其他字段
     * @return 类型的其他字段集合
     */
    public List<SysInfo> selectSysInfoList(SysInfo sysInfo);

    /**
     * 新增类型的其他字段
     *
     * @param sysInfo 类型的其他字段
     * @return 结果
     */
    public int insertSysInfo(SysInfo sysInfo);

    /**
     * 修改类型的其他字段
     *
     * @param sysInfo 类型的其他字段
     * @return 结果
     */
    public int updateSysInfo(SysInfo sysInfo);

    /**
     * 批量删除类型的其他字段
     *
     * @param infoIds 需要删除的类型的其他字段主键集合
     * @return 结果
     */
    public int deleteSysInfoByInfoIds(Long[] infoIds);

    /**
     * 删除类型的其他字段信息
     *
     * @param infoId 类型的其他字段主键
     * @return 结果
     */
    public int deleteSysInfoByInfoId(Long infoId);
}

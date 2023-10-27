package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysTypeMapper;
import com.ruoyi.system.domain.SysType;
import com.ruoyi.system.service.ISysTypeService;

/**
 * 格信息
 - 用于保存多个格 每个格都有自己的名称 类似于ExcelService业务层处理
 *
 * @author ruoyi
 * @date 2023-10-07
 */
@Service
public class SysTypeServiceImpl implements ISysTypeService
{
    @Autowired
    private SysTypeMapper sysTypeMapper;

    /**
     * 查询格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param typeId 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel主键
     * @return 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     */
    @Override
    public SysType selectSysTypeByTypeId(Long typeId)
    {
        return sysTypeMapper.selectSysTypeByTypeId(typeId);
    }

    /**
     * 查询格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel列表
     *
     * @param sysType 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     * @return 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     */
    @Override
    public List<SysType> selectSysTypeList(SysType sysType)
    {
        return sysTypeMapper.selectSysTypeList(sysType);
    }

    /**
     * 新增格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param sysType 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     * @return 结果
     */
    @Override
    public int insertSysType(SysType sysType)
    {
        return sysTypeMapper.insertSysType(sysType);
    }

    /**
     * 修改格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param sysType 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     * @return 结果
     */
    @Override
    public int updateSysType(SysType sysType)
    {
        return sysTypeMapper.updateSysType(sysType);
    }

    /**
     * 批量删除格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param typeIds 需要删除的格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel主键
     * @return 结果
     */
    @Override
    public int deleteSysTypeByTypeIds(Long[] typeIds)
    {
        return sysTypeMapper.deleteSysTypeByTypeIds(typeIds);
    }

    /**
     * 删除格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel信息
     *
     * @param typeId 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel主键
     * @return 结果
     */
    @Override
    public int deleteSysTypeByTypeId(Long typeId)
    {
        return sysTypeMapper.deleteSysTypeByTypeId(typeId);
    }

    @Override
    public int deleteSysTypeInfos(Long[] typeIds) {
        return sysTypeMapper.deleteSysTypeInfos(typeIds);
    }
}

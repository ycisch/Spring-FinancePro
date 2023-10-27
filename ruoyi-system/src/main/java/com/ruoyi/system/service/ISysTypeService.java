package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysType;

/**
 * 格信息
 - 用于保存多个格 每个格都有自己的名称 类似于ExcelService接口
 *
 * @author ruoyi
 * @date 2023-10-07
 */
public interface ISysTypeService
{
    /**
     * 查询格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param typeId 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel主键
     * @return 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     */
    public SysType selectSysTypeByTypeId(Long typeId);

    /**
     * 查询格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel列表
     *
     * @param sysType 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     * @return 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel集合
     */
    public List<SysType> selectSysTypeList(SysType sysType);

    /**
     * 新增格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param sysType 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     * @return 结果
     */
    public int insertSysType(SysType sysType);

    /**
     * 修改格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param sysType 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel
     * @return 结果
     */
    public int updateSysType(SysType sysType);

    /**
     * 批量删除格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel
     *
     * @param typeIds 需要删除的格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel主键集合
     * @return 结果
     */
    public int deleteSysTypeByTypeIds(Long[] typeIds);

    /**
     * 删除格信息
     - 用于保存多个格 每个格都有自己的名称 类似于Excel信息
     *
     * @param typeId 格信息
    - 用于保存多个格 每个格都有自己的名称 类似于Excel主键
     * @return 结果
     */
    public int deleteSysTypeByTypeId(Long typeId);

    public int deleteSysTypeInfos(Long[] typeIds);
}

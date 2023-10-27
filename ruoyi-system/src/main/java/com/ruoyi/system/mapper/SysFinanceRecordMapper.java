package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysFinanceRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 结账记录格Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-26
 */
public interface SysFinanceRecordMapper
{
    /**
     * 查询结账记录格
     *
     * @param id 结账记录格主键
     * @return 结账记录格
     */
    public SysFinanceRecord selectSysFinanceRecordById(Long id);


    /**
     * 查询结账记录格列表
     *
     * @param sysFinanceRecord 结账记录格
     * @return 结账记录格集合
     */
    public List<SysFinanceRecord> selectSysFinanceRecordList(SysFinanceRecord sysFinanceRecord);

    /**
     * 新增结账记录格
     *
     * @param sysFinanceRecord 结账记录格
     * @return 结果
     */
    public int insertSysFinanceRecord(SysFinanceRecord sysFinanceRecord);

    /**
     * 修改结账记录格
     *
     * @param sysFinanceRecord 结账记录格
     * @return 结果
     */
    public int updateSysFinanceRecord(SysFinanceRecord sysFinanceRecord);

    /**
     * 删除结账记录格
     *
     * @param id 结账记录格主键
     * @return 结果
     */
    public int deleteSysFinanceRecordById(Long id);

    /**
     * 批量删除结账记录格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFinanceRecordByIds(Long[] ids);
}

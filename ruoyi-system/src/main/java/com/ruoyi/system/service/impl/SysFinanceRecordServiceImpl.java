package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysFinanceRecordMapper;
import com.ruoyi.system.domain.SysFinanceRecord;
import com.ruoyi.system.service.ISysFinanceRecordService;

/**
 * 结账记录格Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-26
 */
@Service
public class SysFinanceRecordServiceImpl implements ISysFinanceRecordService
{
    @Autowired
    private SysFinanceRecordMapper sysFinanceRecordMapper;

    /**
     * 查询结账记录格
     *
     * @param id 结账记录格主键
     * @return 结账记录格
     */
    @Override
    public SysFinanceRecord selectSysFinanceRecordById(Long id)
    {
        return sysFinanceRecordMapper.selectSysFinanceRecordById(id);
    }

    /**
     * 查询结账记录格列表
     *
     * @param sysFinanceRecord 结账记录格
     * @return 结账记录格
     */
    @Override
    public List<SysFinanceRecord> selectSysFinanceRecordList(SysFinanceRecord sysFinanceRecord)
    {
        return sysFinanceRecordMapper.selectSysFinanceRecordList(sysFinanceRecord);
    }

    /**
     * 新增结账记录格
     *
     * @param sysFinanceRecord 结账记录格
     * @return 结果
     */
    @Override
    public int insertSysFinanceRecord(SysFinanceRecord sysFinanceRecord)
    {
        return sysFinanceRecordMapper.insertSysFinanceRecord(sysFinanceRecord);
    }

    /**
     * 修改结账记录格
     *
     * @param sysFinanceRecord 结账记录格
     * @return 结果
     */
    @Override
    public int updateSysFinanceRecord(SysFinanceRecord sysFinanceRecord)
    {
        return sysFinanceRecordMapper.updateSysFinanceRecord(sysFinanceRecord);
    }

    /**
     * 批量删除结账记录格
     *
     * @param ids 需要删除的结账记录格主键
     * @return 结果
     */
    @Override
    public int deleteSysFinanceRecordByIds(Long[] ids)
    {
        return sysFinanceRecordMapper.deleteSysFinanceRecordByIds(ids);
    }

    /**
     * 删除结账记录格信息
     *
     * @param id 结账记录格主键
     * @return 结果
     */
    @Override
    public int deleteSysFinanceRecordById(Long id)
    {
        return sysFinanceRecordMapper.deleteSysFinanceRecordById(id);
    }
}

package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 格信息用于保存多个格 每个格都有自己的名称 类似于Excel对象 sys_type
 *
 * @author ruoyi
 * @date 2023-10-07
 */
public class SysType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  表格类型id */
    private Long typeId;

    /** 表格名称 */
    @Excel(name = "表格名称")
    private String typeName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date typeTime;

    /** 表格描述 */
    @Excel(name = "表格描述")
    private String typeInfo;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String deleted;

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public Long getTypeId()
    {
        return typeId;
    }
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return typeName;
    }
    public void setTypeTime(Date typeTime)
    {
        this.typeTime = typeTime;
    }

    public Date getTypeTime()
    {
        return typeTime;
    }
    public void setTypeInfo(String typeInfo)
    {
        this.typeInfo = typeInfo;
    }

    public String getTypeInfo()
    {
        return typeInfo;
    }
    public void setDeleted(String deleted)
    {
        this.deleted = deleted;
    }

    public String getDeleted()
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("typeId", getTypeId())
                .append("typeName", getTypeName())
                .append("typeTime", getTypeTime())
                .append("typeInfo", getTypeInfo())
                .append("deleted", getDeleted())
                .toString();
    }
}

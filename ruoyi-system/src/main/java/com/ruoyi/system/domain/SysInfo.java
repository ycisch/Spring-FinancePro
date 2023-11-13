package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 类型的其他字段对象 sys_info
 *
 * @author ruoyi
 * @date 2023-10-30
 */
public class SysInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 其他类型id */
    private Long infoId;

    /** 对应的typeid */
    @Excel(name = "对应的typeid")
    private Long infoTypeId;

    /** 类型(文本 选择框等) */
    @Excel(name = "类型(文本 选择框等)")
    private String infoType;

    /** 其他类型名称 */
    @Excel(name = "其他类型名称")
    private String infoName;

    /** 排序 */
    @Excel(name = "排序")
    private String infoSort;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String deleted;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public Long getInfoId()
    {
        return infoId;
    }
    public void setInfoTypeId(Long infoTypeId)
    {
        this.infoTypeId = infoTypeId;
    }

    public Long getInfoTypeId()
    {
        return infoTypeId;
    }
    public void setInfoType(String infoType)
    {
        this.infoType = infoType;
    }

    public String getInfoType()
    {
        return infoType;
    }
    public void setInfoName(String infoName)
    {
        this.infoName = infoName;
    }

    public String getInfoName()
    {
        return infoName;
    }
    public void setInfoSort(String infoSort)
    {
        this.infoSort = infoSort;
    }

    public String getInfoSort()
    {
        return infoSort;
    }
    public void setDeleted(String deleted)
    {
        this.deleted = deleted;
    }

    public String getDeleted()
    {
        return deleted;
    }
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("infoId", getInfoId())
                .append("infoTypeId", getInfoTypeId())
                .append("infoType", getInfoType())
                .append("infoName", getInfoName())
                .append("infoSort", getInfoSort())
                .append("deleted", getDeleted())
                .append("operator", getOperator())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

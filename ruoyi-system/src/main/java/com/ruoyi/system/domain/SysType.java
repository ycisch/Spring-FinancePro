package com.ruoyi.system.domain;

import java.util.Date;
import java.util.List;

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

    /**
     * 合作状态
     */
    private String typeFlag;

    /**
     * 投资金额
     */
    private String typeMoney;

    /**
     * 表格类型(收入、支出)
     */
    private String typeDesc;

    private List<SysInvestment> typeRecordRefList;

    private List<SysInfo> typeInfoList ;

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public List<SysInfo> getTypeInfoList() {
        return typeInfoList;
    }

    public void setTypeInfoList(List<SysInfo> typeInfoList) {
        this.typeInfoList = typeInfoList;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public List<SysInvestment> getTypeRecordRefList() {
        return typeRecordRefList;
    }

    public void setTypeRecordRefList(List<SysInvestment> typeRecordRefList) {
        this.typeRecordRefList = typeRecordRefList;
    }

    public String getTypeMoney() {
        return typeMoney;
    }

    public void setTypeMoney(String typeMoney) {
        this.typeMoney = typeMoney;
    }

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
        return "SysType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", typeTime=" + typeTime +
                ", typeInfo='" + typeInfo + '\'' +
                ", deleted='" + deleted + '\'' +
                ", typeFlag='" + typeFlag + '\'' +
                ", typeMoney='" + typeMoney + '\'' +
                ", typeDesc='" + typeDesc + '\'' +
                ", typeRecordRefList=" + typeRecordRefList +
                ", typeInfoList=" + typeInfoList +
                '}';
    }
}

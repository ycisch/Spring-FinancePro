package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 财务格主要用于存储多个格之间的信息对象 sys_finance
 *
 * @author ruoyi
 * @date 2023-10-07
 */
public class SysFinance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 财务id */
    private Long financeId;

    /** 财务收入 */
    @Excel(name = "财务收入")
    private BigDecimal financeIncome;

    /** 财务支出 */
    @Excel(name = "财务支出")
    private BigDecimal financeExpenditure;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date financeCreate;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date financeUpdate;

    /** 是否删除 */
    private String deleted;

    /** 财务结余 */
    private BigDecimal financeBalance;

    /** 所属类型() */
    @Excel(name = "所属类型()")
    private int financeType;

    /** 是否结算 */
    private String financeInfo;

    /** 描述-用于添加信息 */
    @Excel(name = "描述-用于添加信息")
    private String financeDec;

    /** 状态(结清，未结) */
    @Excel(name = "状态(结清，未结)")
    private String financeFlag;

    private SysType sysType;

    private List<SysFinanceRecord> sysFinanceRecordList;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结账时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date financeExpendTime;

    public List<SysFinanceRecord> getSysFinanceRecordList() {
        return sysFinanceRecordList;
    }

    public void setSysFinanceRecordList(List<SysFinanceRecord> sysFinanceRecordList) {
        this.sysFinanceRecordList = sysFinanceRecordList;
    }



    public Date getFinanceExpendTime() {
        return financeExpendTime;
    }

    public void setFinanceExpendTime(Date financeExpendTime) {
        this.financeExpendTime = financeExpendTime;
    }

    public SysType getSysType() {
        return sysType;
    }

    public void setSysType(SysType sysType) {
        this.sysType = sysType;
    }

    public void setFinanceId(Long financeId)
    {
        this.financeId = financeId;
    }

    public Long getFinanceId()
    {
        return financeId;
    }
    public void setFinanceIncome(BigDecimal financeIncome)
    {
        this.financeIncome = financeIncome;
    }

    public BigDecimal getFinanceIncome()
    {
        return financeIncome;
    }
    public void setFinanceExpenditure(BigDecimal financeExpenditure)
    {
        this.financeExpenditure = financeExpenditure;
    }

    public BigDecimal getFinanceExpenditure()
    {
        return financeExpenditure;
    }
    public void setFinanceCreate(Date financeCreate)
    {
        this.financeCreate = financeCreate;
    }

    public Date getFinanceCreate()
    {
        return financeCreate;
    }
    public void setFinanceUpdate(Date financeUpdate)
    {
        this.financeUpdate = financeUpdate;
    }

    public Date getFinanceUpdate()
    {
        return financeUpdate;
    }
    public void setDeleted(String deleted)
    {
        this.deleted = deleted;
    }

    public String getDeleted()
    {
        return deleted;
    }
    public void setFinanceBalance(BigDecimal financeBalance)
    {
        this.financeBalance = financeBalance;
    }

    public BigDecimal getFinanceBalance()
    {
        return financeBalance;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public void setFinanceInfo(String financeInfo)
    {
        this.financeInfo = financeInfo;
    }

    public String getFinanceInfo()
    {
        return financeInfo;
    }
    public void setFinanceDec(String financeDec)
    {
        this.financeDec = financeDec;
    }

    public String getFinanceDec()
    {
        return financeDec;
    }
    public void setFinanceFlag(String financeFlag)
    {
        this.financeFlag = financeFlag;
    }

    public String getFinanceFlag()
    {
        return financeFlag;
    }

    @Override
    public String toString() {
        return "SysFinance{" +
                "financeId=" + financeId +
                ", financeIncome=" + financeIncome +
                ", financeExpenditure=" + financeExpenditure +
                ", financeCreate=" + financeCreate +
                ", financeUpdate=" + financeUpdate +
                ", deleted='" + deleted + '\'' +
                ", financeBalance=" + financeBalance +
                ", financeType=" + financeType +
                ", financeInfo='" + financeInfo + '\'' +
                ", financeDec='" + financeDec + '\'' +
                ", financeFlag='" + financeFlag + '\'' +
                ", sysType=" + sysType +
                ", financeExpendTime=" + financeExpendTime +
                '}';
    }
}

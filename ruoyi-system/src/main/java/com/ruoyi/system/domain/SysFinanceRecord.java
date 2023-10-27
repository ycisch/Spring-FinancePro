package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 结账记录格对象 sys_finance_record
 *
 * @author ruoyi
 * @date 2023-10-26
 */
public class SysFinanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 对应的当前记录信息 */
    @Excel(name = "对应的当前记录信息")
    private Long financeIds;

    /** 结账时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结账时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordTime;

    /** 结账金额 */
    @Excel(name = "结账金额")
    private BigDecimal recordMoney;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public Long getFinanceIds() {
        return financeIds;
    }

    public void setFinanceIds(Long financeIds) {
        this.financeIds = financeIds;
    }

    public void setRecordTime(Date recordTime)
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime()
    {
        return recordTime;
    }
    public void setRecordMoney(BigDecimal recordMoney)
    {
        this.recordMoney = recordMoney;
    }

    public BigDecimal getRecordMoney()
    {
        return recordMoney;
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
                .append("id", getId())
                .append("financeIds", getFinanceIds())
                .append("recordTime", getRecordTime())
                .append("recordMoney", getRecordMoney())
                .append("operator", getOperator())
                .toString();
    }
}

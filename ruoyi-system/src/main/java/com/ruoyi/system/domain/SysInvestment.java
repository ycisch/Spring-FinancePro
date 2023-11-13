package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投资信息对象 sys_investment
 *
 * @author ruoyi
 * @date 2023-10-27
 */
public class SysInvestment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 表格id */
    @Excel(name = "表格id")
    private Long typeId;

    /** 投资人 */
    @Excel(name = "投资人")
    private String investor;

    /** 金额 */
    @Excel(name = "金额")
    private String investment;

    /** 创建人 */
    @Excel(name = "创建人")
    private String operator;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public Long getTypeId()
    {
        return typeId;
    }
    public void setInvestor(String investor)
    {
        this.investor = investor;
    }

    public String getInvestor()
    {
        return investor;
    }
    public void setInvestment(String investment)
    {
        this.investment = investment;
    }

    public String getInvestment()
    {
        return investment;
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
                .append("typeId", getTypeId())
                .append("investor", getInvestor())
                .append("investment", getInvestment())
                .append("createTime", getCreateTime())
                .append("operator", getOperator())
                .toString();
    }
}

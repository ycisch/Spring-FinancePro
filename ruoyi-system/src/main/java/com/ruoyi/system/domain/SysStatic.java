package com.ruoyi.system.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysStatic {


    //表格名称
    private String staticName;

    //表格ID
    private Long typeId;

    //支出
    private BigDecimal expendMoney;

    //收入
    private BigDecimal incomeMoney;

    //总支出
    private BigDecimal expendMoneyAll;

    //总收入
    private BigDecimal incomeMoneyAll;

    //支出结账金额
    private BigDecimal expendRecordMoney;

    //收入结账金额
    private BigDecimal incomeRecordMoney;

}
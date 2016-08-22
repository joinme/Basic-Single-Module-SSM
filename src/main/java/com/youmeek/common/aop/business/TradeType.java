package com.youmeek.common.aop.business;

/**
 * User: cgj 交易类型
 * Date: 2015/8/12
 * Time: 18:35
 * Created by Administrator on 2015/8/12.
 */
public enum TradeType {
	
	TRADE_TYPE_RECHARGE(12, "充值"),
	TRADE_TYPE_PURCHASE(12, "申购"),
	TRADE_TYPE_CASH(12, "取现"),
	TRADE_TYPE_REDEEM(12, "赎回"),
	TRADE_TYPE_PAYROLL(12, "代发工资"),
	TRADE_TYPE_PURCHASE_SALARY(12, "工资代购"),
	TRADE_TYPE_DIVIDENDS(12, "修改分红方式"),
	TRADE_TYPE_DEPOSIT(12, "存款"),
	TRADE_TYPE_WITHDRAW(12, "取款"),
	TRADE_TYPE_CONVERT(12, "宝类互转"),
	
	;
	private final int value;
	private final String desc;

	String getDesc() {
		return desc;
	}
	int getValue(){
		return value;
	}
	TradeType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
}

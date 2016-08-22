package com.youmeek.common.aop.business;

/**
 * User: cgj 订单状态
 * Date: 2015/8/12
 * Time: 18:36
 * Created by Administrator on 2015/8/12.
 */
public enum TradeStatus {
	
	TRADE_STATUS_PENDING(13,"待处理"),
	TRADE_STATUS_ACCEPTED(13,"已受理"),
	TRADE_STATUS_SUCCESS(13,"交易成功"),
	TRADE_STATUS_FAIL(13,"交易失败"),
	TRADE_STATUS_CLOSED(13,"已关闭"),
	TRADE_STATUS_IMPOERTED(13,"已导入"),
	TRADE_STATUS_UNPAYED(13,"未支付"),
	TRADE_STATUS_PAY_SUCCESS(13,"支付成功"),
	
//	NULL //无此项值时
	;
	private final int value;
	private final String desc;

	String getDesc() {
		return desc;
	}
	int getValue(){
		return value;
	}
	TradeStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
}

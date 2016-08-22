package com.youmeek.common.aop.business;


/**
 * User: cgj 订单状态
 * Date: 2015/8/12
 * Time: 18:37
 * Created by Administrator on 2015/8/12.
 */
public enum OrderStatus {
	
	ORDER_STATUS_UNPAYED(15, "未支付"),
	ORDER_STATUS_PAY_SUCCESS(15, "支付成功"),
	ORDER_STATUS_TO_BE_CONFIRMED(15, "待确认"),
	ORDER_STATUS_ACCEPTED(15, "已受理"),
	ORDER_STATUS_SUCCESS(15, "成功"),
	ORDER_STATUS_FAIL(15, "失败"),
	ORDER_STATUS_TOCONFIRMED(15, "待确认"),
	ORDER_STATUS_TOAUDIT(15, "待复核"),
	ORDER_STATUS_AUDIT_PASS(15, "复核通过"),
	ORDER_STATUS_AUDIT_UNPASS(15, "复核不通过"),
	ORDER_STATUS_CANCLED(15, "已取消"),
	ORDER_STATUS_IMPORTED(15, "已导入"),
//	NULL // 无此项值时
	;
	private final int value;
	private final String desc;
	String getDesc() {
		return desc;
	}
	int getValue(){
		return value;
	}
	OrderStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
}

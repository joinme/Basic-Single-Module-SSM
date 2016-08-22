package com.youmeek.common.aop.business;


/**
 * User: Administrator
 * Date: 2015/8/12
 * Time: 18:36
 * Created by Administrator on 2015/8/12.
 */
public enum OrderSource {
	
	BUSINESS_TYPE_FUND(1,"普通基金 "),//普通基金 (多渠道都是)
	BUSINESS_TYPE_FLB(2,"福利宝"),//福利宝
	BUSINESS_TYPE_INSURANCE(3,"保险理财"),//保险理财
	BUSINESS_TYPE_P2P(4,"p2p类"),//p2p类
	BUSINESS_TYPE_NON_STANDARD(5,"非标理财"),//非标理财
	BUSINESS_TYPE_OTHER_FUND(6,"其他理财"),//其他理财
	BUSINESS_TYPE_HQB(7,"活期宝"),//活期宝
	BUSINESS_TYPE_DQB(8,"定期宝"),//定期宝
	BUSINESS_TYPE_ZSB(9,"指数宝")//指数宝
	;
	private final int value;
	private final String desc;

	String getDesc() {
		return desc;
	}
	int getValue(){
		return value;
	}
	OrderSource(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
}

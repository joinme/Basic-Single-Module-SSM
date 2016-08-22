package com.youmeek.common.aop.business;

import java.lang.annotation.*;

/**
 * User: Administrator
 * Date: 2015/7/9
 * Time: 18:25
 * Created by Administrator on 2015/7/9.
 * TradeType tradeStatus orderType orderStatus
 *
 * tradeType    开户0（不存），1充值，2 申购3 取现4 赎回5代发工资，6工资代购 7 修改分红方式8存款9取款10宝类互转
 * tradeStatus  6已导入（初始态） 1待处理（企业）2已受理3交易成功4交易失败5已关闭7未支付8支付成功
 * orderSource 订单来源  通businesstype--1普通基金 (多渠道都是)2福利宝（）3保险理财4 p2p类5 非标理财6 其他理财 7活期宝 8定期宝9指数宝
 * orderStatus 20已导入（初始）19已取消（企业）18复核不通过（企业）17复核通过（企业）16待复核（企业）15待确认14失败13成功12申请成功11支付成功10未支付
 * timeOut     超时时间
 *
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
//@Inherited
public @interface OrderTradeProcess {
	//设置订单超时时间 单位是秒
	long timeOut()
			default 0
			;
	//设置订单编号的方法参数名
	String orderNo()
//			default ""
			;
	//设置订单的用户
	String userId();
	//设置交易类型
	TradeType tradeType();
	//设置交易状态
	TradeStatus[] tradeStatus()
//			default TradeStatus.NULL
			;
	//设置订单来源
	OrderSource orderSource();
	//设置订单状态
	OrderStatus[] orderStatus()
//			default OrderStatus.NULL
			;
	/*
	//	交易类型
	enum TradeType {
		TRADE_TYPE_RECHARGE,//充值
		TRADE_TYPE_BUY,//购买
		TRADE_TYPE_CASH,//取现
		TRADE_TYPE_REDEEM,//赎回
		TRADE_TYPE_SALARY//代发工资
	}
	//	交易状态
	enum TradeStatus {
		TRADE_STATUS_UNPROCESSED,//待处理
		TRADE_STATUS_ACCEPTED,//已受理
		TRADE_STATUS_SUCCESS,//交易成功
		TRADE_STATUS_FAIL,//交易失败
		TRADE_STATUS_CLOSED,//已关闭
		TRADE_STATUS_IMPOERTED,//已导入
		NULL //无此项值时
	}
	//	订单来源
	enum OrderSource {
		BUSINESS_TYPE_FUND,//普通基金 (多渠道都是)
		BUSINESS_TYPE_FLB,//福利宝
		BUSINESS_TYPE_INSURANCE,//保险理财
		BUSINESS_TYPE_P2P,//p2p类
		BUSINESS_TYPE_NON_STANDARD,//非标理财
		BUSINESS_TYPE_OTHER_FUND,//其他理财
		BUSINESS_TYPE_HQB,//活期宝
		BUSINESS_TYPE_DQB,//定期宝
		BUSINESS_TYPE_ZSB//指数宝
	}
	//	订单状态
	enum OrderStatus {
		ORDER_STATUS_UNPAYED,//未支付
		ORDER_STATUS_PAY_SUCCESS,//支付成功
		ORDER_STATUS_APPLY_SUCCESS,//申请成功
		ORDER_STATUS_BUY_SUCCESS,//购买成功
		ORDER_STATUS_REDEEM_SUCCESS,//赎回成功
		ORDER_STATUS_UNCONFIRMED,//待确认
		ORDER_STATUS_UNCHECKED,//待复核
		ORDER_STATUS_CHECK_PASSED,//审核通过
		ORDER_STATUS_CHECK_UNPASSED,//审核未通过
		ORDER_STATUS_CANCLED,//取消
		ORDER_STATUS_IMPORTED,//已导入
		NULL // 无此项值时
	}
	*/
}

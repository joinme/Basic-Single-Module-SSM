package com.youmeek.common.aop.business;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * User: cgj
 * Date: 2015/8/12
 * Time: 13:07
 * Created by Administrator on 2015/8/12.
 */
//@Aspect
//@Component
public class OrderTradeAspect {
	/*
	@Resource
	private OrderService orderService;
	@Resource
	private TradeService tradeService;

	private String typeName;

	private BaseModel returnMessage(String result, String message) throws ApiValidationException {
		if (typeName.indexOf("BaseModel") < 0) {
			throw new ApiValidationException(message);
		} else {
			return new BaseModel(result, message);
		}
	}

	@Pointcut("@annotation(com.hjb.common.aop.OrderTradeProcess)")
	public void OrderTradePointcut() {
	}
	*/
	//	@Before("OrderTradePointcut()")
	@Around("OrderTradePointcut()")
	public Object orderTradeProcessRound(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
//		String methodName =  joinPoint.getSignature().getName();

		//获取方法参数值
		List<Object> args = Arrays.asList(joinPoint.getArgs());
//		System.out.println("方法传的参数" + args.toString());

		//获取方法名
		String methodName = method.getName();

		//获取方法参数名称
		String[] methodParamNames = getMethodParamNames(method);
		List<String> paramNames = Arrays.asList(methodParamNames);

		//获取方法中的参数类型
		Class[] parameterTypes = method.getParameterTypes();

		//获取方法的返回值类型
		Class returnType = method.getReturnType();

		//获取方法注解的值
		OrderTradeProcess orderTradeProcess = method.getAnnotation(OrderTradeProcess.class);
		TradeType tradeType = orderTradeProcess.tradeType();
		TradeStatus[] tradeStatuses = orderTradeProcess.tradeStatus();
		OrderSource orderSource = orderTradeProcess.orderSource();
		OrderStatus[] orderStatuses = orderTradeProcess.orderStatus();
		String orderNoStr = orderTradeProcess.orderNo();
		String userIdStr = orderTradeProcess.userId();
		/*
//		TODO 暂时注掉
//		long timeOut = orderTradeProcess.timeOut();

		typeName = returnType.getName();

//		 判断订单编号 和 用户编号 参数名有没有设置
		if (Util.isNullOrBlank(orderNoStr) || !paramNames.contains(orderNoStr)) {
			return returnMessage(Constant.FAIL, "订单编号参数名必须指定");
		}

		if (Util.isNullOrBlank(userIdStr) || !paramNames.contains(userIdStr)) {
			return returnMessage(Constant.FAIL, "用户编号参数名必须指定");
		}

//		 判断订单编号 和 用户编号 参数值有没有设置
		int i = paramNames.indexOf(orderNoStr);
		String orderNo = null;
		Object o = args.get(i);
		if (o instanceof String) {
			orderNo = (String) o;
		} else if (o instanceof FundOrder) {
			FundOrder fundOrder = (FundOrder) o;
			if (fundOrder == null || com.hjb.common.util.Util.isNullOrBlank(fundOrder.getOrderNo())) {
				return returnMessage(Constant.FAIL, "订单编号参数值不能为空");
			}
			orderNo = fundOrder.getOrderNo();
		}

		i = paramNames.indexOf(userIdStr);
		String userId = null;
		o = args.get(i);
		if (o instanceof String) {
			userId = (String) o;
		} else if (o instanceof User) {
			User user = (User) o;
			if (user == null || com.hjb.common.util.Util.isNullOrBlank(user.getUserId())) {
				return returnMessage(Constant.FAIL, "用户编号参数值不能为空");
			}
			userId = user.getUserId();
		}

//		 获取 订单 交易
		FundOrder order = new FundOrder();
		order.setOrderNo(orderNo);
		order.setUserId(userId);
		order = orderService.queryOrder(order);
		*//*
		order = orderService.queryOrder(orderNo);
		if (!userId.equals(order.getUserId())) {
			return returnMessage(Constant.FAIL, "用户名错误");
		}
		*//*
		if (order == null) {
			return returnMessage(Constant.FAIL, "用户编号错误或订单编号错误");
		}
//		 判断订单来源
		if (order.getOrderResource() != orderSource.getValue()) {
			return returnMessage(Constant.FAIL, "订单来源非预期，预期订单来源为：" + orderSource.getDesc());
		}
//		 判断订单是否超时 TODO 暂时注掉
//		if (timeOut > 0) {
//			long orderTime = order.getCreateTime().getTime();
//			if ((System.currentTimeMillis() - orderTime) / 1000 > timeOut) {
//				return returnMessage(Constant.FAIL, "订单超时了");
//			}
//		}
//		 判断 订单状态
		boolean flag = true;
		StringBuilder message = new StringBuilder("");
		for (OrderStatus orderStatus : orderStatuses) {
			message.append(orderStatus.getDesc() + ",");
			if (orderStatus.getValue() == order.getStatus()) {
				flag = false;
				break;
			}
		}
		if (flag) {
			message.deleteCharAt(message.lastIndexOf(","));
			return returnMessage(Constant.FAIL, "订单状态非预期，预期订单状态为：" + message);
		}

//		 获取 交易
		Trade trade = new Trade();
		trade.setOrderNo(orderNo);
		trade.setUserId(userId);
		trade = tradeService.queryTrade(trade);
		if (trade == null) {
			return returnMessage(Constant.FAIL, "用户编号错误或订单编号错误");
		}

//		 判断 交易状态
		flag = true;
		message = new StringBuilder("");
		for (TradeStatus tradeStatus : tradeStatuses) {
			message.append(tradeStatus.getDesc() + ",");
			if (tradeStatus.getValue() == trade.getStatus()) {
				flag = false;
				break;
			}
		}
		if (flag) {
			message.deleteCharAt(message.lastIndexOf(","));
			return returnMessage(Constant.FAIL, "交易状态非预期，预期交易状态为：" + message);
		}
//		flag = true;
//		message = new StringBuilder("");

//		 判断 交易类型
		if (trade.getTradeType() != tradeType.getValue()) {
			return returnMessage(Constant.FAIL, "交易类型非预期，预期交易类型为：" + tradeType.getDesc());
		}
*/

//		System.out.println(orderNoStr);
//		System.out.println(timeOut);
//		System.out.println(Arrays.asList(tradeStatuses).toString());
//		System.out.println(tradeType);
//		System.out.println(orderSource);
//		System.out.println(Arrays.asList(orderStatuses).toString());

//		System.out.println(Arrays.asList(methodParamNames).toString());

//		if (method.isAnnotationPresent(OrderTradeProcess.class)) {
//		}

		/*
		System.out.println("方法名称：" + method.getName());
		System.out.println("是否带有可变参数变量:" + method.isVarArgs());
		Class<?>[] parameterTypes = method.getParameterTypes();//获得方法所有的参数类型
		for (int i = 0; i < parameterTypes.length; i++) {
			System.out.println("parameterTypes[" + i + "]" + parameterTypes[i].getName());
		}

		System.out.println("返回值类型：" + method.getReturnType()); //获得方法返回值类型
		System.out.println("可能抛出异常类型有：");
		Class[] exceptionTypes = method.getExceptionTypes();  //获得可能抛出的所有异常类型
		for (int j = 0; j < exceptionTypes.length; j++) {
			System.out.println("exceptionTypes[" + j + "]" + exceptionTypes[j]);
		}
		*/
		return joinPoint.proceed();
	}

	/**
	 * <p>获取方法的参数名</p>
	 *
	 * @param m
	 * @return
	 */
	public static String[] getMethodParamNames(final Method m) {
		final String[] paramNames = new String[m.getParameterTypes().length];
		final String n = m.getDeclaringClass().getName();
		final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		String className = m.getDeclaringClass().getSimpleName();
		ClassReader cr = null;
		InputStream resourceAsStream = null;
		try {
//			cr = new ClassReader(n);
//			String filePathName = Class.forName(n).getResource("EDayHqbProcessManagerImpl.class").getPath();
			resourceAsStream = Class.forName(n).getResourceAsStream(className + ".class");
			cr = new ClassReader(resourceAsStream);
//			cr = new ClassReader(ClassLoader.getSystemResourceAsStream(n + ".class"));
		} catch (IOException e) {
			e.printStackTrace();
//			Exceptions.uncheck(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (resourceAsStream != null) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		assert cr != null;
		cr.accept(new ClassVisitor(Opcodes.ASM4, cw) {
			@Override
			public MethodVisitor visitMethod(final int access,
			                                 final String name, final String desc,
			                                 final String signature, final String[] exceptions) {
				final Type[] args = Type.getArgumentTypes(desc);
				// 方法名相同并且参数个数相同
				if (!name.equals(m.getName())
						|| !sameType(args, m.getParameterTypes())) {
					return super.visitMethod(access, name, desc, signature,
							exceptions);
				}
				MethodVisitor v = cv.visitMethod(access, name, desc, signature,
						exceptions);
				return new MethodVisitor(Opcodes.ASM4, v) {
					@Override
					public void visitLocalVariable(String name, String desc,
					                               String signature, Label start, Label end, int index) {
						int i = index - 1;
						// 如果是静态方法，则第一就是参数
						// 如果不是静态方法，则第一个是"this"，然后才是方法的参数
						if (Modifier.isStatic(m.getModifiers())) {
							i = index;
						}
						if (i >= 0 && i < paramNames.length) {
							paramNames[i] = name;
						}
						super.visitLocalVariable(name, desc, signature, start,
								end, index);
					}

				};
			}
		}, 0);

		return paramNames;
	}


	/**
	 * <p>比较参数类型是否一致</p>
	 *
	 * @param types   asm的类型({@link Type})
	 * @param clazzes java 类型({@link Class})
	 * @return
	 */
	private static boolean sameType(Type[] types, Class<?>[] clazzes) {
		// 个数不同
		if (types.length != clazzes.length) {
			return false;
		}

		for (int i = 0; i < types.length; i++) {
			if (!Type.getType(clazzes[i]).equals(types[i])) {
				return false;
			}
		}
		return true;
	}

}

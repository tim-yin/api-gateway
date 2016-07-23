package com.niwodai.app.gateway.exception.enums;

/**
 * @author tim.yin
 * @date 2015年12月10日 上午11:52:38
 * @version 1.0
 * @Description:这里可以自定义异常返回码 推荐跟httpstatus 报错一致。原先
 */

public enum ResponseCode {

	// 成功处理
	SUCCESS("200", "处理成功"),

	// 通用错误异常
	ERROR("500", "处理失败"),

	// 跳转异常，至于跳到什么页面，是客户端自己控制
	REDIRECTERROR("300", "跳转异常"),

	// 购买跳转异常 购买金额大于 标的可购余额 专用
	PURCHASEAMOUNTERROR("301", "购买金额跳转异常"),

	// 验签异常
	VALIDATORFAILURE("400", "验签失败"),

	// 用户权限的异常
	FREEZEUSERFAILURE("601",
			"“尊敬的你我贷用户，检测到您的账户存在异常，为了您的资金安全暂时冻结您的账户，如有疑问请致电客服：400-7910-888”。"), BLACKLISTUSERFAILURE(
			"602", "您在你我贷平台有高风险操作，为确保您和他人的资产安全，您已不能充值。详询4007-910-888"), ILLEGALCHARACTER(
			"603", "请求包含非法字符【<、>、\"、'、&】,请去掉后重试"),

	ROUTESERVICEFAILURE("602", "路由服务异常！"),

	REQUESTVALIDATORFAILURE("701", "请求通过拦截器异常！");

	public final String code;

	public final String codeMessage;

	private ResponseCode(String code, String codeMessage) {
		this.code = code;
		this.codeMessage = codeMessage;
	}

	public String getCode() {
		return code;
	}

	public String getCodeMessage() {
		return codeMessage;
	}

}

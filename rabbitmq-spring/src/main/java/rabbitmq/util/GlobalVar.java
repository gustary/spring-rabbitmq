package rabbitmq.util;

import java.util.HashMap;

/**
 * 
 * 文件名：GlobalVar.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月29日下午4:32:33
 */
public class GlobalVar {

	/** 消息接受处理成功*/
	public static final String MESSAGE_HANDLE_ACCEPT = "message_handle_accept";
	/** 消息处理失败可重试 */
	public static final String MESSAGE_HANDLE_RETRY = "message_handle_retry";
	/** 消息处理失败不可重试 */
	public static final String MESSAGE_HANDLE_REJECT = "message_handle_reject";

	public static HashMap<String, String> parameterMap = new HashMap<String, String>();

}

package wl.common.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <日志打印>
 * 日志打印格式规范
 * @author WL
 * @date 2019/3/7
 */
public class Log {
	// 业务分类打印标记定义
	public final static String DEMO_USER_CONSUMER = "[DEMO_USER_CONSUMER]";

	private Logger logger;

	@SuppressWarnings("rawtypes")
	public Log(Class clazz) {
		logger = LogManager.getLogger(clazz);
	}

	public Log(String name) {
		logger = LogManager.getLogger(name);
	}

	public Log(Logger logger) {
		this.logger = logger;
	}

	/**
	 * 请求日志打印
	 * @param data
	 */
	public void reqPrint( String busName,String data) {
		if ((data != null)) {
			logger.info(getLineNum()  + "busName"+busName+" data=" + data.replaceAll("\\s{1,}", ""));
		}
	}


	/**
	 * 响应日志打印
	 * @param data
	 */
	public void respPrint(String busName,String data) {
        if (data != null) {
            logger.info(getLineNum() + "busName"+busName+" data=" + data);
        }
    }

	/**
	 * info 日志打印
	 * @param msg
	 */
	public void info( String msg) {
		logger.info(getLineNum() + " msg:" + msg);
	}

	/**
	 * warn 日志打印
	 * @param msg
	 */
	public void warn(String msg) {
		logger.warn(getLineNum() + msg);
	}

	/**
	 * error 日志打印
	 * @param msg
	 * @param e
	 */
	public void error( String msg, Exception e) {
		logger.error(getLineNum() +  " msg:" + msg, e);
	}

	/**
	 * debug 日志打印
	 * @param msg
	 */
	public void debug(String msg) {
		logger.debug(getLineNum() + msg);
	}

	/**
	 * 获取行号
	 * @return
	 */
	public String getLineNum() {
		// 读取栈信息获取真正行号
		Throwable throwable = new Throwable();
		StackTraceElement[] ste = throwable.getStackTrace();
		return "[" + ste[2].getLineNumber() + "] - ";
	}
}

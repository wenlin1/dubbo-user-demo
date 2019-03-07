package com.wl.base.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 自定义重新加载log4j2 配置文件
 * @author WL
 * @description TODO
 * @date 2019/3/7
 */
@SuppressWarnings("rawtypes")
@Component
public class CustomerLog4j2Initializer implements ApplicationListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLog4j2Initializer.class);

	private static final String DEFAULT_LOG4J2_FILEPATH = "classpath:log/log4j2.xml";
	
	private static final String DEFAULT_LOGPATH = "/data/{ip}/demo-user-consumer-default";
	
	public static final String XML_FILE_EXTENSION = ".xml";
	

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		LOGGER.info("CustomerLog4j2Initializer onApplicationEvent");
		// 容器启动完成之后load  
        if (event instanceof ContextRefreshedEvent) {
            if (((ContextRefreshedEvent) event).getApplicationContext().getParent() == null) {
            	LOGGER.info("CustomerLog4j2Initializer , reloading log4j2");
            	
                try {
					reloadLog4j2();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
                LOGGER.info("CustomerLog4j2Initializer , reloaded log4j2 successful");
            }  
        }
	}

	
	/**
	 * 重新加载log4j2配置文件
	 * @throws FileNotFoundException
	 * @return void
	 */
	private void reloadLog4j2() throws FileNotFoundException {
		LOGGER.info("reloadLog4j2 , load log4j2 properties");
		//最终日志存放的真实路径
		String realLogPath = "";
		//获取diamond配置中心中配置的日志路径
		realLogPath = DEFAULT_LOGPATH;
		
		//解析日志存放路径中的变量，替换ip
		realLogPath = realLogPath.replace("{ip}", packageIp());
		
		//获取log4j配置文件路径，如无配置则默认加载classpath:log4j2.xml
		String location = DEFAULT_LOG4J2_FILEPATH;
		
		LOGGER.info("reloadLog4j2 , location:"+location+",realLogPath:"+realLogPath);
		
		//将日志存放路径设置至jvm变量中，供log4j配置文件中加载
		System.setProperty("reallogpath", realLogPath);
		
		//重新加载log4j2配置文件
		String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
		if (resolvedLocation.toLowerCase().endsWith(XML_FILE_EXTENSION)) {
			LOGGER.info("reloadLog4j2 , LoggerContext.reconfigure--start");
			
		    File file = ResourceUtils.getFile(resolvedLocation);
			LoggerContext context =(LoggerContext) LogManager.getContext(false);
			context.setConfigLocation(file.toURI());
			LOGGER.info("reloadLog4j2 , LoggerContext.setConfigLocation:"+file.toURI());
			//重新初始化Log4j2的配置上下文  
			context.reconfigure();  
			
			LOGGER.info("reloadLog4j2 , LoggerContext.reconfigure--finished");
		}
		else {
			//暂不支持非xml配置
		}
		
	}
	
	private static String packageIp(){
		return getIp().replace(".", "_");
	}

	private static String getIp(){
		String ipAddr = "";
		Properties p = System.getProperties();
		String os = p.getProperty("os.name");
		//如果是windows操作系统
		if(null != os && os.startsWith("Windows")){
			try {
				InetAddress addr = InetAddress.getLocalHost();
				ipAddr = addr.getHostAddress().toString();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}else{
			//定义网络接口枚举类
			Enumeration<NetworkInterface> allNetInterfaces;
	        try {
				//获得网络接口
	            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				//声明一个InetAddress类型ip地址
	            InetAddress ip = null;
				//遍历所有的网络接口
	            while (allNetInterfaces.hasMoreElements())
	            {  
	                NetworkInterface netInterface = allNetInterfaces.nextElement();
					//同样再定义网络地址枚举类
	                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	                while (addresses.hasMoreElements())  
	                {  
	                    ip = addresses.nextElement();  
	                    if( ip.isSiteLocalAddress()   
	    			            && !ip.isLoopbackAddress()
	    			            && ip instanceof Inet4Address
	    			            && ip.getHostAddress().indexOf(":")==-1)   
	    			    {
	                    	ipAddr = ip.getHostAddress();
	    			        return ipAddr;   
	    			    }
	                }
	            }
	        } catch (SocketException e) {
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
		}
		return ipAddr;
	}
	
}

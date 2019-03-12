package rabbitmq.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 文件名：InitProxy.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月29日下午4:28:59
 */
public class InitProxy {

	public void init() throws Exception {
		initProperties("rabbitmq.properties");
	}

	public static boolean initProperties(String filename) {
		Properties reportch = new Properties();
		InputStream reportchIs = InitProxy.class.getClassLoader().getResourceAsStream(filename);
		try {
			// 因为文件为utf-8格式 所以读取的时候使用utf-8格式读取，不然中文乱码
			reportch.load(new InputStreamReader(reportchIs, "UTF-8"));
			Enumeration enmObject = reportch.keys();
			// 对每一个主键信息进行检索处理，跟传入的返回值信息是否有相同的地方（如果有相同的地方，取出主键信息的属性，传回给返回信息）
			while (enmObject.hasMoreElements()) {
				String curKey = (String) enmObject.nextElement();
				String curMessage = reportch.getProperty(curKey);
				GlobalVar.parameterMap.put(curKey, curMessage);
			}
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (reportchIs != null)
					reportchIs.close();
			} catch (IOException e) {
				return false;
			} finally {
				reportchIs = null;
			}
		}
		return true;
	}

}

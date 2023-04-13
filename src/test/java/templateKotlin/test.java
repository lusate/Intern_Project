package templateKotlin;

import com.omnilab.templateKotlin.common.AES256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {

	private static final Logger logger = LoggerFactory.getLogger("test");

	public static void main(String[] args) {
		logger.error("{}", AES256.enCode("12"));
		logger.error("{}", AES256.enCode("123"));
		logger.error("{}", AES256.enCode("1234"));
		logger.error("{}", AES256.enCode("12345"));
	}
	
}

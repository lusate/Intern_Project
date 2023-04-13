package templateKotlin;

import com.omnilab.templateKotlin.common.AES256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class test {

	private static final Logger logger = LoggerFactory.getLogger("test");

	public static void main(String[] args) {
		logger.error("{}", AES256.enCode("테스트"));
		logger.error("{}", AES256.deCode(AES256.enCode("테스트")));
		logger.error("{}", AES256.deCode("_Oktn06L7A5_dvWBRjVghViT9E2NC16zLA"));
	}
	
}

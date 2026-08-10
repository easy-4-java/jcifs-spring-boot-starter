package org.jcifs.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jcifs.CIFSException;
import jcifs.context.BaseContext;
import jcifs.context.SingletonContext;
import jcifs.smb.SmbFile;

@Configuration
@ConditionalOnClass({ SmbFile.class })
@EnableConfigurationProperties({SmbProperties.class })
/**\n * Auto-configuration for SmbAutoConfiguration.\n *\n * @author <a href="https://github.com/loong10k">Loong Wan</a>\n * @since 1.0.0\n */
public class SmbAutoConfiguration {

	@Bean
	public BaseContext baseContext() throws CIFSException {
		SingletonContext.init(null);
		return SingletonContext.getInstance();
	}
	
}

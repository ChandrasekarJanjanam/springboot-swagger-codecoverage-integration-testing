package com.chandra.microservice.v1.api;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration.BeanPostProcessorsRegistrar;
import org.springframework.boot.context.embedded.EmbeddedServletContainerException;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import javax.servlet.Servlet;
import java.io.File;

/**
 * This class is used to create spring container in user directory rather than temp dir.
 * @author Chandra
 *
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication
@Import(BeanPostProcessorsRegistrar.class)
public class CustomEmbeddedServletContainer extends EmbeddedServletContainerAutoConfiguration {

	@Configuration
	@ConditionalOnClass({ Servlet.class, Tomcat.class })
	@ConditionalOnMissingBean(value = EmbeddedServletContainerFactory.class, search = SearchStrategy.CURRENT)
	public static class EmbeddedTomcat {

		@Bean
		public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
			return new WdpTomcatEmbeddedServletContainerFactory();
		}

	}
	
	/**
	 * @author Chandra
	 *
	 */
	public static class WdpTomcatEmbeddedServletContainerFactory extends TomcatEmbeddedServletContainerFactory{

		/* (non-Javadoc)
		 * @see org.springframework.boot.context.embedded.AbstractEmbeddedServletContainerFactory#createTempDir(java.lang.String)
		 */
		@Override
		protected File createTempDir(String prefix) {
			

			try {
				String  filePath = System.getProperty("user.dir")+"/target/"+prefix + "."+getPort();
				File tempDir = new File(filePath);
				tempDir.delete();
				tempDir.mkdir();
				tempDir.deleteOnExit();
				return tempDir;
			}
			catch (Exception ex) {
				throw new EmbeddedServletContainerException(
						"Unable to create Dir. user.dir is set to "
								+ System.getProperty("user.dir"),
						ex);
			}
		}
	}
}

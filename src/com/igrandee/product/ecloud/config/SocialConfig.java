package com.igrandee.product.ecloud.config;



import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.web.DisconnectController;

import com.googlecode.googleplus.Plus;
import com.googlecode.googleplus.core.GooglePlusConnectionFactory;

@Configuration
@EnableSocial
@PropertySource(value = { "classpath:application.properties" })
public class SocialConfig implements SocialConfigurer {

	@Inject
	private DataSource dataSource;
	
	@Inject
	private UsersConnectionRepository usersConnectionRepository;
	

	public void addConnectionFactories(
			ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		connectionFactoryConfigurer
				.addConnectionFactory(new FacebookConnectionFactory(environment
						.getProperty("facebook.clientId"), environment
						.getProperty("facebook.clientSecret")));
		connectionFactoryConfigurer.addConnectionFactory(new GooglePlusConnectionFactory(
				environment.getProperty("googleplus.clientId"),
				environment.getProperty("googleplus.clientSecret")));
		if(!environment.getProperty("http.proxyHost").equals("localhost")){
		System.setProperty("http.proxyHost",environment.getProperty("http.proxyHost"));
		System.setProperty("http.proxyPort",environment.getProperty("http.proxyPort"));
		}
	}

	public UserIdSource getUserIdSource() {
		return new UserIdSource() {
			public String getUserId() {
				Authentication authentication = SecurityContextHolder
						.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException(
							"Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}

	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		return new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
	}

	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		ConnectController connectController = new ConnectController(
				connectionFactoryLocator, connectionRepository);
		return connectController;
	}

	@Bean
	public DisconnectController disconnectController(
			UsersConnectionRepository usersConnectionRepository,
			Environment environment) {
		return new DisconnectController(usersConnectionRepository,
				environment.getProperty("facebook.clientSecret"));
	}
	
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook() {
		
		ConnectionRepository repository=usersConnectionRepository.createConnectionRepository(getUserIdSource().getUserId());
		Connection<Facebook> connection = repository
				.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}
	
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Plus plus(){
		
	     ConnectionRepository repository=usersConnectionRepository.createConnectionRepository(getUserIdSource().getUserId());
	     Connection<Plus> connection=repository.findPrimaryConnection(Plus.class);
	     return connection != null ? connection.getApi() : null;
		
	}

}

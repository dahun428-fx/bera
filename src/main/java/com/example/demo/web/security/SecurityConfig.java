package com.example.demo.web.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
//@Configuration 클래스에 @EnableWebSecurity 어노테이션을 추가하여 Spring Security 설정할 클래스라고 정의합니다.
//설정은 WebSebSecurityConfigurerAdapter 클래스를 상속받아 메서드를 구현하는 것이 일반적인 방법입니다. 
@EnableWebSecurity
//WebSecurityConfigurer 인스턴스를 편리하게 생성하기 위한 클래스입니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	//비밀번호를 암호화하는 패스워드인코드를 스프링의 빈으로 등록하기
	//passwordEncoder()
	//BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체입니다.
	//Service에서 비밀번호를 암호화할 수 있도록 Bean으로 등록합니다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
	/*
	 * configure(WebSecurity web)
		WebSecurity는 FilterChainProxy를 생성하는 필터입니다.
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
		해당 경로의 파일들은 Spring Security가 무시할 수 있도록 설정합니다.
		즉, 이 파일들은 무조건 통과하며, 파일 기준은 resources/static 디렉터리입니다. ( css, js 등의 디렉터리를 추가하진 않았습니다. )
	 */
	public void configure(WebSecurity web) throws Exception {
		//static 디렉터리의 하위 파일 목록은 인증 무시
		web
			.ignoring().antMatchers("/css/**","/js/**","/img/**");
		
	}
	/*
	 * configure(HttpSecurity http)
		HttpSecurity를 통해 HTTP 요청에 대한 웹 기반 보안을 구성할 수 있습니다.
		authorizeRequests()
		HttpServletRequest에 따라 접근(access)을 제한합니다.
		antMatchers() 메서드로 특정 경로를 지정하며, permitAll(), hasRole() 메서드로 역할(Role)에 따른 접근 설정을 잡아줍니다. 여기서 롤은 권한을 의미합니다. 즉 어떤 페이지는 관리지만 접근해야 하고, 어떤 페이지는 회원만 접근해야할 때 그 권한을 부여하기 위해 역할을 설정하는 것입니다. 예를 들어,
		.antMatchers("/admin/**").hasRole("ADMIN")
		/admin 으로 시작하는 경로는 ADMIN 롤을 가진 사용자만 접근 가능합니다.
		.antMatchers("/user/myinfo").hasRole("MEMBER")
		/user/myinfo 경로는 MEMBER 롤을 가진 사용자만 접근 가능합니다.
		.antMatchers("/**").permitAll()
		모든 경로에 대해서는 권한없이 접근 가능합니다.
		.anyRequest().authenticated()
		모든 요청에 대해, 인증된 사용자만 접근하도록 설정할 수도 있습니다. ( 예제에는 적용 안함 )
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				//접근 권한 제어 구성
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/order/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/mypage/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/", "/**").access("permitAll")
			.and()
				.formLogin()
				.loginPage("/user/login")
				.defaultSuccessUrl("/")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true);
			
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
}

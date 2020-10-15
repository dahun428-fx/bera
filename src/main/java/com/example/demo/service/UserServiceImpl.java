package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.UserDao;
import com.example.demo.vo.User;
import com.example.demo.vo.UserRole;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Spring Security Authentication 객체에서 로그인된 유저 객체를
	 * 비밀번호를 제외하여 반환한다.
	 * 
	 */
	public User getLoginedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User savedUser = (User) authentication.getPrincipal();
		User newUser = new User();
		newUser.setName(savedUser.getName());
		newUser.setId(savedUser.getId());
		newUser.setPhone(savedUser.getPhone());
		newUser.setEmail(savedUser.getEmail());
		
		return newUser;
	}
	/**
	 * 지정된 사용자 아이디에 해당하는 사용자 정보를 반환한다.
	 * 
	 * <p>
	 * UserDetails 인터페이스를 구현한 {@link com.example.demo.vo.User} 객체가 반환된다.
	 * 
	 * <p>
	 * User객체에는 사용자 정보(아이디, 비번, 이름 ...), 접근권한(List<GrantedAuthority>), 계정관련정보(만료여부, 잠김여부, 비밀번호 사용기한경과 여부 ...)를 포함하고 있다.
	 * 
	 * <p>
	 * 로그인 입력화면에서 아이디와 비밀번호를 입력하고 로그인 요청을 서버로 보내면, 스프링 시큐리티가 이 메소드를 실행해서 User객체를 조회한다.
	 * 조회된 User객체의 비밀번호와 입력한 비밀번호를 암호화한 비밀번호가 일치하는지 조사해서 인증여부를 결정한다.<br>
	 * 인증인 완료되면 인증된 사용자 정보를 포함하고 있는 Authentication 객체를 SecurityContextHolder에 저장한다.
	 * 
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 사용자 아이디로 사용자 정보를 조회한다.
		// User객체(UserDetails 인터페이스를 구현한)에는 위의 값이 저장되어 있음
		Map<String, Object> map = new HashMap<>();
		map.put("query", "getUserById");
		map.put("userId", username);
		User user = userDao.getUser(map);
		if(user != null) {
			Collection<GrantedAuthority> authorities = this.getAuthorities(user.getId());
			user.setAuthorities(authorities);
			return user;
		}
		
		throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
	}
	/**
	 * 지정한 사용자 아이디로 조회한 사용자의 접근권한 정보를 반환한다.
	 * 
	 * 지정된 사용자 아이디로 권한 정보를 조회한다.
	 * 권한명으로 {@link SimpleGrantedAuthority} 객체를 생성해서 콜렉션에 담고 반환한다.
	 * @param userId 사용자아이디
	 * @return 사용자가 보유하고 있는 권한 정보 
	 */
	Collection<GrantedAuthority> getAuthorities(String userId){
		List<UserRole> roles = userDao.getUserRolesByUserId(userId);
		
		if(roles.isEmpty()) {
			throw new UsernameNotFoundException("접근 권한 정보가 존재하지 않습니다.");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(UserRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		System.out.println("getAuthorities : " + authorities);
		return authorities;
	}
	
	/**
	 * 회원가입 기능 구현
	 */
	public Map<String, Object> join(User user) {
		//user 객체 password encoder, 보안
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//mybatis parameter 정의
		Map<String, Object> param = new HashMap<>();
		//join return msg
		Map<String, Object> msg = new HashMap<>();
		
		param.put("query", "getUserById");
		param.put("userId", user.getId());
		//user Id 중복검사
		User duplicatedUser = userDao.getUser(param);
		if(duplicatedUser != null) {
			msg.put("type", "error");
			msg.put("msg", "이미 존재하는 아이디입니다.");
			return msg;
		}
		param = new HashMap<>();
		param.put("query", "getUserByEmail");
		param.put("email", user.getEmail());
		//user Email 중복검사
		User duplicatedEmail = userDao.getUser(param);
		if(duplicatedEmail != null) {
			msg.put("type", "error");
			msg.put("msg", "이미 존재하는 이메일입니다.");
			return msg;
		}
		//회원 DB에 등록
		userDao.insertUser(user);
		//회원 Role DB에 등록
		userDao.insertUserRole(user.getId(), "ROLE_USER");
		//성공시 redirect 할 url 입력
		msg.put("type", "success");
		msg.put("msg", "/");
		return msg;
	}
}

package org.marker.mushroom.spring.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

//import com.mywish.mywish.domain.user.Role;
//import com.mywish.mywish.domain.user.User;
//import com.mywish.mywish.service.UserManager;

/**
 * spring2.0支持数据库，如果要使用去掉注释
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author jayd
 * @since 1.0
 */
/*
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	*//**
	 * 获取用户Details信息的回调函数.
	 *//*
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		User user = userManager.findUserByLoginName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + userName + " 不存在");
		}
		boolean enabled = true;
		if (user.getStatus() == 0) {
			enabled = false;
			throw new UsernameNotFoundException("用户" + userName + " 未激活");
		}
		GrantedAuthority[] grantedAuths = obtainGrantedAuthorities(user);

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantedAuths);

		return userdetail;
	}

	*//**
	 * 获得用户所有角色的权限集合.
	 *//*
	private GrantedAuthority[] obtainGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			authSet.add(new GrantedAuthorityImpl(role.getRole_name()));
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
*/
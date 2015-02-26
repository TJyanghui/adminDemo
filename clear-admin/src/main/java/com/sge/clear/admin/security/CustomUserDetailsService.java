package com.sge.clear.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;

import com.sge.clear.admin.dao.UserDOMapper;
import com.sge.clear.admin.model.UserDO;

/**
 * 
 * <p>
 * UserDetailService 给用户提供自定义的授权处理
 * 
 * @author yh
 * @see UserDetailService
 */

public class CustomUserDetailsService implements UserDetailsService {

	@Resource(name = "userDOMapper")
	private UserDOMapper userDOMapper;

	/*
	 * 
	 * <p>用户访问应用资源之前,将会调用此方法获取用户的登录信息及对应的权限范围(ROLE_USER,ROLE_ADMIN)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */

	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException, DataAccessException {

		UserDetails userDetails = null;

		// 根据用户名得到用户对象
		UserDO user = userDOMapper.selectByPrimaryKey(username);
		if(user==null)  
            throw new UsernameNotFoundException(username + " not exist!");
		
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user.getRole());

		// Populate the Spring User object with details from the user
		boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true;        
		userDetails = new User(user.getUsername(), user.getPassword().toLowerCase(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked,grantedAuths);

		return userDetails;
	}

	/**
	 * 获得用户的角色权限
	 * @param role
	 * @return
	 */
	public Collection<GrantedAuthority> obtionGrantedAuthorities(String role) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		authList.add(new SimpleGrantedAuthority(role));

		return authList;
	}

}

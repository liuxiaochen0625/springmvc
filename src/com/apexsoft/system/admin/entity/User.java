package com.apexsoft.system.admin.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * 自定义用户信息表
 * function 
 * @author zlzhang
 * 2013-12-12上午11:39:35
 */
public class User  implements UserDetails,Serializable{
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;  //用户名
	private String realName;  //真实姓名
	private String passWord;  //密码
	private Boolean issys;     //是否是超级用户
	private Boolean enabled;   //是否过期
	/**
	 * 用户权限集合
	 */
	private Set<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocaed;
	private boolean credentialsNonExpired;
	
	
	public User() {
	}
	public User(int id, String userName, String passWord, Boolean issys,
			Boolean enabled, Collection<GrantedAuthority> authorities,
			boolean accountNonExpired, boolean accountNonLocaed,
			boolean credentialsNonExpired) {
		if(null == userName || "".equals(userName) || null == passWord || "".equals(passWord)){
			logger.error("Cannot pass null or empty values to constructor");
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.issys = issys;
		this.enabled = enabled;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocaed = accountNonLocaed;
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public Boolean getIssys() {
		return issys;
	}
	public void setIssys(Boolean issys) {
		this.issys = issys;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isAccountNonLocaed() {
		return accountNonLocaed;
	}
	public void setAccountNonLocaed(boolean accountNonLocaed) {
		this.accountNonLocaed = accountNonLocaed;
	}
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	//-------实现继承接口的方法
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	@Override
	public String getPassword() {
		return this.passWord;
	}
	@Override
	public String getUsername() {
		return this.userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocaed;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return this.isEnabled();
	}
	
	//-------一些自定义的method
	
	public boolean equals(Object obj){
		if(!(obj instanceof User) || (null == obj)){
			return false;
		}
		User user = (User)obj;
		//具有的权限
		if(!authorities.equals(user.getAuthorities())){
			return false;
		}
		//通过Spring Security构建一个用户时，用户名和密码不能为空！
		return (this.getUserName().equals(user.getUserName()) && this.getPassWord().equals(user.getPassWord()));
	}
	
	public int hashCode(){
		int code = 9772;
		
		//如果用户不是登录人员，则可以允许没有authorities
		if (null != getUsername() && null != getAuthorities()) {
			for (GrantedAuthority authority : getAuthorities()) {

				code = code * (authority.hashCode() % 7);
			}
		}

        if (this.getPassword() != null) {
            code = code * (this.getPassword().hashCode() % 7);
        }

        if (this.getUsername() != null) {
            code = code * (this.getUsername().hashCode() % 7);
        }

        if (this.isAccountNonExpired()) {
            code = code * -2;
        }

        if (this.isAccountNonLocked()) {
            code = code * -3;
        }

        if (this.isCredentialsNonExpired()) {
            code = code * -5;
        }

        if (this.isEnabled()) {
            code = code * -7;
        }
        return code;
	}
	
	public String toString(){
		return this.userName+this.passWord;
	}
	
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
	
	

}

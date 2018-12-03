package com.imooc.security.browser.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;

/**
 * Created by mrt on 2017/11/23.
 */
//jpa数据自动生成表及字段
@Entity(name = "users")
@Data
public class TUser implements UserDetails{
    //设置ID并自动增长
    @Id
    @GeneratedValue
    private Integer id;
//    @NotBlank(message = "这个字段必填")
    private String username;
    //@设定最小值为18，低于18校验不通过，并出现错误信息
//    @Min(value = 18,message = "未成年禁止")
    private String password;
//    @NotNull(message = "金额必传")
    private Integer role;

    private String telPhone;

    public TUser(String username, String password) {
        this.username = username;
        this.password = password;
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
    }

    public TUser(String username, String password, boolean b, boolean b1, boolean b2, boolean b3, Collection<? extends GrantedAuthority> authorities) {
    }

    public TUser(String username, String password,
                Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


}

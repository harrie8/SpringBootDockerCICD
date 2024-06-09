package com.sppart.admin.sub.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class Users {
    private final Long user2_id;
    private final String id;
    @JsonIgnore
    private final String password;
    private final Role role;

    @Builder
    public Users(Long user2_id, String id, String password, Role role) {
        this.user2_id = user2_id;
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }
}

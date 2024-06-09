package com.sppart.admin.sub.user.domain;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class Accessor {

    private final String id;
    private final Role role;

    @Builder
    public Accessor(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return role.getGrantedAuthorities();
    }
}

package com.sppart.admin.sub.user.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
@JsonInclude
public enum Role {
    SUPER_ADMIN("MANAGE_USER,MANAGE_BOARD,MANAGE_ART,MANAGE_PW,MANAGE_ADMIN"),
    ADMIN("MANAGE_USER,MANAGE_BOARD,MANAGE_ART"),
    GUEST("NONE"),
    ;

    private final String authorities;

    public boolean equals(Role other) {
        return this == other;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        Collection<GrantedAuthority> result = new ArrayList<>();
        for (String authority : this.authorities.split(",")) {
            result.add(new SimpleGrantedAuthority(authority));
        }
        return result;
    }
}
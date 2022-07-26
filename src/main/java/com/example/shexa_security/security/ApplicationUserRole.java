package com.example.shexa_security.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.shexa_security.security.ApplicationUserPermission.*;
//<dependency>
//<groupId>com.google.guava</groupId>
//<artifactId>guava</artifactId>
//<version>28.1-jre</version>
//</dependency>
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),//Zero permissions added to student role
    ADMIN(Sets.newHashSet(STUDENT_READ,STUDENT_WRITE,COURSE_READ,COURSE_WRITE)),//four permissions added to Admin role

    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));//four permissions added to Admin role

    private final Set<ApplicationUserPermission> permissions;
    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}

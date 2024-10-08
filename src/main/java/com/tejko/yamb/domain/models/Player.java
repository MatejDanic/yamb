package com.tejko.yamb.domain.models;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "player")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "player", indexes = {
    @Index(name = "idx_player_external_id", columnList = "external_id")
})
@DiscriminatorFormula("CASE WHEN password IS NULL THEN 'GUEST' ELSE 'REGISTERED' END")
public abstract class Player implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "external_id", nullable = false, updatable = false, unique = true)
    private UUID externalId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = true) // password is null for guest users
    private String password;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Score> scores;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Log> logs;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "player_role", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PlayerPreferences preferences;

    protected Player() {}

    protected Player(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Score> getScores() {
        return scores;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public PlayerPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(PlayerPreferences preferences) {
        this.preferences = preferences;
    }
    
    @Override  
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getLabel()))
            .collect(Collectors.toList());
    }

    @Override    
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override  
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override  
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override  
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    private void ensureExternalId() {
        if (this.externalId == null) {
            this.externalId = UUID.randomUUID();
        }
    }

    @Override 
    public String getName() {
        return String.valueOf(externalId);
    }
    
}

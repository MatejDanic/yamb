package com.tejko.yamb.domain.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tejko.yamb.domain.models.AnonymousPlayer;
import com.tejko.yamb.domain.models.Player;
import com.tejko.yamb.domain.models.PlayerWithToken;
import com.tejko.yamb.domain.models.RegisteredPlayer;
import com.tejko.yamb.domain.models.Role;
import com.tejko.yamb.domain.repositories.PlayerRepository;
import com.tejko.yamb.domain.repositories.RoleRepository;
import com.tejko.yamb.domain.services.interfaces.AuthService;
import com.tejko.yamb.security.AuthContext;
import com.tejko.yamb.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final PlayerRepository playerRepo;
    private final RoleRepository roleRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authManager, PlayerRepository playerRepo, RoleRepository roleRepo, 
            JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.authManager = authManager;
        this.playerRepo = playerRepo;
        this.roleRepo = roleRepo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @Override
    public PlayerWithToken login(String username, String password) {
        RegisteredPlayer player = authenticate(username, password);
        PlayerWithToken playerWithToken = new PlayerWithToken(player, jwtUtil.generateToken(player.getId()));
        return playerWithToken;
    }

    @Override
    public RegisteredPlayer register(String username, String password) {
        Optional<Player> authenticatedPlayer = AuthContext.getAuthenticatedPlayer();

        RegisteredPlayer player;

        if (authenticatedPlayer.isPresent() && Hibernate.getClass(authenticatedPlayer.get()).equals(AnonymousPlayer.class)) {
            if (!authenticatedPlayer.get().getUsername().equals(username)) {
                validateUsername(username);
            }
            player = (RegisteredPlayer) authenticatedPlayer.get();
            player.setUsername(username);
            player.setPassword(encoder.encode(password));
        } else {
            validateUsername(username);
            player = RegisteredPlayer.getInstance(username, encoder.encode(password), getDefaultRoles());
        }

        return playerRepo.save(player);
    }
    
    @Override
    public PlayerWithToken createAnonymousPlayer(String username) {
        validateUsername(username);
        AnonymousPlayer player = AnonymousPlayer.getInstance(username, getDefaultRoles());

        player = playerRepo.save(player);
        PlayerWithToken playerWithToken = new PlayerWithToken(player, jwtUtil.generateToken(player.getId()));
        return playerWithToken;
    }

    private void validateUsername(String username) {
        if (playerRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("error.username_taken");
        }
    }

    private RegisteredPlayer authenticate(String username, String password) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (RegisteredPlayer) authentication.getPrincipal();
    }

    private Set<Role> getDefaultRoles() {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepo.findByLabel("USER")
            .orElseThrow(() -> new ResourceNotFoundException("error.not_found.role"));
        roles.add(userRole);
        return roles;
    }

}
package com.tejko.yamb.api.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tejko.yamb.api.controllers.AuthController;
import com.tejko.yamb.api.controllers.PlayerController;
import com.tejko.yamb.api.dto.responses.AuthResponse;
import com.tejko.yamb.domain.models.PlayerWithToken;

@Component
public class AuthModelAssembler implements RepresentationModelAssembler<PlayerWithToken, AuthResponse> {

    private final ModelMapper modelMapper;

    @Autowired
    public AuthModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthResponse toModel(PlayerWithToken playerWithToken) {

        AuthResponse authResponse = modelMapper.map(playerWithToken, AuthResponse.class);
        authResponse.add(linkTo(methodOn(AuthController.class).resetPassword(null)).withRel("reset-password"));
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).getByExternalId(authResponse.getPlayer().getId())).withSelfRel());
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).getScoresByPlayerExternalId(authResponse.getPlayer().getId())).withRel("scores"));
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).getClashesByPlayerExternalId(authResponse.getPlayer().getId())).withRel("clashes"));
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).getLogsByPlayerExternalId(authResponse.getPlayer().getId())).withRel("logs"));
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).getPreferencesByPlayerExternalId(authResponse.getPlayer().getId())).withRel("preferences"));
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).changeUsernameByExternalId(authResponse.getPlayer().getId(), null)).withRel("username"));
        authResponse.getPlayer().add(linkTo(methodOn(PlayerController.class).getPlayerStatsByExternalId(authResponse.getPlayer().getId())).withRel("stats"));
        
        return authResponse;
    }

}

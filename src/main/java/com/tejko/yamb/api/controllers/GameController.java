package com.tejko.yamb.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejko.yamb.api.dto.requests.ActionRequest;
import com.tejko.yamb.api.dto.requests.GameRequest;
import com.tejko.yamb.api.dto.responses.GameResponse;
import com.tejko.yamb.domain.services.interfaces.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {

	private final GameService gameService;
	private final ModelMapper modelMapper;

	@Autowired
	public GameController(GameService gameService, ModelMapper modelMapper) {
		this.gameService = gameService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GameResponse> getById(@PathVariable String id) {
		GameResponse gameResponse = modelMapper.map(gameService.getById(id), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

	@GetMapping("")
	public ResponseEntity<List<GameResponse>> getAll() {
		List<GameResponse> gameResponses = gameService.getAll().stream().map(game -> modelMapper.map(game, GameResponse.class)).collect(Collectors.toList());
		return ResponseEntity.ok(gameResponses);
	}

	@PutMapping("")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResponse> getOrCreate(@Valid @RequestBody GameRequest gameRequest) {
		GameResponse gameResponse = modelMapper.map(gameService.getOrCreate(gameRequest.getPlayerId()), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

	@PutMapping("/{id}/roll")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResponse> rollById(@PathVariable String id, @Valid @RequestBody ActionRequest actionRequest) {
		GameResponse gameResponse = modelMapper.map(gameService.rollById(id, actionRequest.getDiceToRoll()), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

	@PutMapping("/{id}/announce")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResponse> announceById(@PathVariable String id, @Valid @RequestBody ActionRequest actionRequest) {
		GameResponse gameResponse = modelMapper.map(gameService.announceById(id, actionRequest.getBoxType()), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

	@PutMapping("/{id}/fill")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResponse> fillById(@PathVariable String id, @Valid @RequestBody ActionRequest actionRequest) {
		GameResponse gameResponse = modelMapper.map(gameService.fillById(id, actionRequest.getColumnType(), actionRequest.getBoxType()), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

	@PutMapping("/{id}/restart")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResponse> restartById(@PathVariable String id) {
		GameResponse gameResponse = modelMapper.map(gameService.restartById(id), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

	@PutMapping("/{id}/finish")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<GameResponse> finishById(@PathVariable String id) {
		GameResponse gameResponse = modelMapper.map(gameService.finishById(id), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}
	
	@PutMapping("/{id}/complete")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<GameResponse> completeById(@PathVariable String id) {
		GameResponse gameResponse = modelMapper.map(gameService.completeById(id), GameResponse.class);
		return ResponseEntity.ok(gameResponse);
	}

}

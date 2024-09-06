package com.tejko.yamb.api.dto.responses;

import java.time.LocalDateTime;

public class ScoreResponse {

    private Long id;
    private LocalDateTime createdAt;
    private ShortPlayerResponse player;
    private int value;

    public ScoreResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ShortPlayerResponse getPlayer() {
        return player;
    }

    public void setPlayer(ShortPlayerResponse player) {
        this.player = player;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
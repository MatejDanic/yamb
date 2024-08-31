package com.tejko.yamb.api.dto.responses;

import java.time.LocalDateTime;

public class PlayerStatsResponse {

    private LocalDateTime lastActivity;
    private double averageScore;
    private ScoreResponse highScore;
    private long scoreCount;

    public PlayerStatsResponse() {}
    
    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public ScoreResponse getHighScore() {
        return highScore;
    }

    public void setHighScore(ScoreResponse highScore) {
        this.highScore = highScore;
    }

    public long getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(long scoreCount) {
        this.scoreCount = scoreCount;
    }

}

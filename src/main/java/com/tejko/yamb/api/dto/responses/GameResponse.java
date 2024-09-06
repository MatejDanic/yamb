package com.tejko.yamb.api.dto.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.tejko.yamb.domain.enums.BoxType;
import com.tejko.yamb.domain.enums.ColumnType;
import com.tejko.yamb.domain.enums.GameStatus;

public class GameResponse {
 
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ShortPlayerResponse player;
    private Sheet sheet;
    private List<Dice> dices;
    private int rollCount;
    private BoxType announcement;
    private GameStatus status;
    private int totalSum;

    public GameResponse() {}
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ShortPlayerResponse getPlayer() {
        return player;
    }

    public void setPlayer(ShortPlayerResponse player) {
        this.player = player;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public List<Dice> getDices() {
        return dices;
    }

    public void setDices(List<Dice> dices) {
        this.dices = dices;
    }

    public int getRollCount() {
        return rollCount;
    }

    public void setRollCount(int rollCount) {
        this.rollCount = rollCount;
    }

    public BoxType getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(BoxType announcement) {
        this.announcement = announcement;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public static class Dice {

        private int index;
        private int value;

        public Dice() {}

        public Dice(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    
    }

    public static class Sheet {

        private List<Column> columns;

        public Sheet() {}

        public Sheet(List<Column> columns) {
            this.columns = columns;
        }

        public List<Column> getColumns() {
            return columns;
        }

        public void setColumns(List<Column> columns) {
            this.columns = columns;
        }

    }

    public static class Column {

        private ColumnType type;
        private List<Box> boxes;

        public Column() {}

        public Column(ColumnType type, List<Box> boxes) {
            this.type = type;
            this.boxes = boxes;
        }

        public ColumnType getType() {
            return type;
        }

        public void setType(ColumnType type) {
            this.type = type;
        }

        public List<Box> getBoxes() {
            return boxes;
        }

        public void setBoxes(List<Box> boxes) {
            this.boxes = boxes;
        }

    }

    public static class Box {

        private BoxType type;
        private Integer value;

        public Box() {}

        public Box(BoxType type, Integer value) {
            this.type = type;
            this.value = value;
        }

        public BoxType getType() {
            return type;
        }

        public void setType(BoxType type) {
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    
    }
    
}


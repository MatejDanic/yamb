package com.tejko.yamb.models.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tejko.yamb.models.enums.BoxType;
import com.tejko.yamb.models.enums.ColumnType;

public class Sheet implements Serializable {

    private List<Column> columns;

    private Sheet() { }

    private Sheet(List<Column> columns) {
        this.columns = columns;
    }

    public static Sheet getInstance() {
        return new Sheet(generateColumns());
    }

    private static List<Column> generateColumns() {
        List<Column> columns = new ArrayList<>();
        for (ColumnType columnType : ColumnType.values()) {
            columns.add(Column.getInstance(columnType));
        }
        return columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    @JsonIgnore
    public int getTopSectionSum() {  
        int topSectionSum = 0;
        for (Column column : columns) {
            topSectionSum += column.getTopSectionSum();
        }
        return topSectionSum;
    }

    @JsonIgnore
    public int getMiddleSectionSum() {
        int middleSectionSum = 0;
        for (Column column : columns) {
            middleSectionSum += column.getMiddleSectionSum();
        }
        return middleSectionSum;
    }

    @JsonIgnore
    public int getBottomSectionSum() {
        int bottomSectionSum = 0;
        for (Column column : columns) {
            bottomSectionSum += column.getBottomSectionSum();
        }
        return bottomSectionSum;
    }
    
    @JsonIgnore
    public int getTotalSum() { 
        return getTopSectionSum() + getMiddleSectionSum() + getBottomSectionSum();
    }
    
    @JsonIgnore
    public boolean isCompleted() {
        for (Column column : columns) {
            if (!column.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    public void fillBox(ColumnType columnType, BoxType boxType, int value) {
        columns.get(columnType.ordinal()).fillBox(boxType, value);
    }

    public boolean areAllNonAnnouncementColumnsCompleted() {
        for (Column column : columns) {
            if (column.getType() != ColumnType.ANNOUNCEMENT && !column.isCompleted()) {
                return false;
            }
        }
        return true;
    }

}

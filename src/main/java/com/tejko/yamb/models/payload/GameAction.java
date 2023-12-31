package com.tejko.yamb.models.payload;

import com.tejko.yamb.models.enums.BoxType;
import com.tejko.yamb.models.enums.ColumnType;

public class GameAction {

    private int[] diceToRoll;
    private ColumnType columnType;
    private BoxType boxType;

    private GameAction() {}
    
    private GameAction(int[] diceToRoll, ColumnType columnType, BoxType boxType) {
        this.diceToRoll = diceToRoll;
        this.columnType = columnType;
        this.boxType = boxType;
    }

    public int[] getDiceToRoll() {
        return diceToRoll;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public BoxType getBoxType() {
        return boxType;
    }
    
}

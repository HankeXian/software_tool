package edu.uob;

import java.util.ArrayList;

public class OXOModel {

    private ArrayList<ArrayList<OXOPlayer>> cells;
    private OXOPlayer[] players;
    private int currentPlayerNumber;
    private boolean IsAct;
    private OXOPlayer winner;
    private boolean gameDrawn;
    private int winThreshold;

    public OXOModel(int numberOfRows, int numberOfColumns, int winThresh) {
        winThreshold = winThresh;
        IsAct = true;
        cells = new ArrayList<>();
        for (int i =0; i<numberOfRows; i++){
            ArrayList<OXOPlayer> row = new ArrayList<>();
            for(int j =0; j<numberOfColumns; j++){
                row.add(null);
            }
            cells.add(row);
        }
        players = new OXOPlayer[2];
    }

    public int getNumberOfPlayers() {
        return players.length;
    }

    public void addPlayer(OXOPlayer player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                return;
            }
        }
    }

    public OXOPlayer getPlayerByNumber(int number) {
        return players[number];
    }

    public OXOPlayer getWinner() {
        return winner;
    }

    public void setWinner(OXOPlayer player) {
        winner = player;
        IsAct = false;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int playerNumber) {
        currentPlayerNumber = playerNumber;
    }

    public int getNumberOfRows() {
        return cells.size();
    }

    public int getNumberOfColumns() {
        if(!cells.isEmpty()){
            return cells.get(0).size();
        }
        else{
            return 0;
        }
    }

    public OXOPlayer getCellOwner(int rowNumber, int colNumber) {
        return cells.get(rowNumber).get(colNumber);
    }

    public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {
        cells.get(rowNumber).set(colNumber, player);
    }

    public void addRow(){
        ArrayList<OXOPlayer> newRow = new ArrayList<>();
        int colNum = cells.get(0).size();
        for(int i =0; i<colNum; i++){
            newRow.add(null);
        }
        cells.add(newRow);
    }
    public void addCol(){
        for(ArrayList<OXOPlayer> row : cells){
            row.add(null);
        }
    }
    public void removeRow(){
        if(!cells.isEmpty()){
            cells.remove(cells.size()-1);
        }
    }
    public void removeCol(){
        if(!cells.isEmpty() && !cells.get(0).isEmpty()){
            for(ArrayList<OXOPlayer> row : cells){
                row.remove(row.size()-1);
            }
        }
    }
    public void setWinThreshold(int winThresh) {
        winThreshold = winThresh;
    }

    public int getWinThreshold() {
        return winThreshold;
    }

    public void setAct(boolean act) {
        IsAct = act;
    }

    public boolean getIsAct() {
        return IsAct;
    }

    public void setGameDrawn(boolean isDrawn) {
        gameDrawn = isDrawn;
    }

    public boolean isGameDrawn() {
        return gameDrawn;
    }

}

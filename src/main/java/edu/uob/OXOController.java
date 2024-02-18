package edu.uob;

import edu.uob.OXOMoveException.*;

public class OXOController {
    OXOModel gameModel;

    public OXOController(OXOModel model) {
        gameModel = model;
    }

    public void handleIncomingCommand(String command) throws OXOMoveException {
        if(!gameModel.getIsAct()){
            return;
        }
        if(command.length()!=2){
            throw new InvalidIdentifierLengthException(command.length());
        }

        char chRow = command.charAt(0);
        char chCol = command.charAt(1);
        int row = command.charAt(0)-'a';
        int col = command.charAt(1)-'1';

        if(Character.isUpperCase(chRow)){
            chRow = Character.toLowerCase(chRow);
            row = command.charAt(0)- 'A';
        }
        if(!Character.isDigit(chCol)){
            throw new InvalidIdentifierCharacterException(RowOrColumn.COLUMN, chCol);
        }
        else if(!Character.isLetter(chRow)){
            throw new InvalidIdentifierCharacterException(RowOrColumn.ROW, chRow);
        }
        if(col< 0 || col >= gameModel.getNumberOfColumns()){
            throw new OutsideCellRangeException(RowOrColumn.COLUMN, col);
        }
        if(row < 0 || row >= gameModel.getNumberOfRows()){
            throw new OutsideCellRangeException(RowOrColumn.ROW, row);
        }

        if(gameModel.getCellOwner(row, col) != null){
            throw new CellAlreadyTakenException(row, col);
        }

        gameModel.setCellOwner(row, col, gameModel.getPlayerByNumber(gameModel.getCurrentPlayerNumber()));

        if(check_Hor_Ver()||checkMajor()||checkMinor()){
            OXOPlayer player = gameModel.getPlayerByNumber(gameModel.getCurrentPlayerNumber());
            gameModel.setWinner(player);
        }
        else if(!checkDraw()){
            gameModel.setGameDrawn(true);
        }
        else{
            int i = (gameModel.getCurrentPlayerNumber()+1)%gameModel.getNumberOfPlayers();
            gameModel.setCurrentPlayerNumber(i);
        }
    }
    public void addRow() {
        if(!gameModel.getIsAct()){
            return;
        }
        if(gameModel.getNumberOfRows() < 9){
            gameModel.addRow();
        }
    }
    public void removeRow() {
        if(gameModel.getNumberOfRows() > 3 && rowMov()){
            gameModel.removeRow();
        }
    }
    public void addColumn() {
        if(!gameModel.getIsAct()){
            return;
        }
        if(gameModel.getNumberOfColumns() < 9){
            gameModel.addCol();
        }
    }
    public void removeColumn() {
        if(gameModel.getNumberOfColumns() > 3 && colMov()){
            gameModel.removeCol();
        }
    }
    private boolean rowMov(){
        int rowNum = gameModel.getNumberOfRows() - 1;
        for(int i = 0; i < gameModel.getNumberOfColumns(); i++){
            if(gameModel.getCellOwner(rowNum, i) != null) {
                return false;
            }
        }
        return true;
    }
    private boolean colMov(){
        int colNum = gameModel.getNumberOfColumns() - 1;
        for(int i = 0; i < gameModel.getNumberOfRows(); i++){
            if(gameModel.getCellOwner(i, colNum) != null) {
                return false;
            }
        }
        return true;
    }
    private boolean check_Hor_Ver(){
        int winNum = gameModel.getWinThreshold();
        OXOPlayer player = gameModel.getPlayerByNumber(gameModel.getCurrentPlayerNumber());
        for(int i =0; i<gameModel.getNumberOfRows(); i++) {
            int count_row = 0;
            for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
                if (player == gameModel.getCellOwner(i, j)) {
                    count_row++;
                    if (count_row == winNum) {
                        return true;
                    }
                }
            }
        }

        for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
            int count_col = 0; // Reset count_col for each column
            for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
                // Vertical check
                if (player == gameModel.getCellOwner(i, j)) {
                    count_col++;
                    if (count_col == winNum) {
                        return true;
                    }
                } else {
                    count_col = 0; // Reset if sequence is broken
                }
            }
        }
        return false;
    }
    private boolean checkMajor() {
        int winThreshold = gameModel.getWinThreshold();
        int numberOfRows = gameModel.getNumberOfRows();
        int numberOfColumns = gameModel.getNumberOfColumns();

        // Loop through all possible starting points for a win in major diagonal
        for (int row = 0; row <= numberOfRows - winThreshold; row++) {
            for (int col = 0; col <= numberOfColumns - winThreshold; col++) {
                OXOPlayer currentPlayer = gameModel.getCellOwner(row, col);
                if (currentPlayer == null) continue; // Skip if cell is unclaimed

                boolean win = true;
                for (int i = 1; i < winThreshold; i++) {
                    if (gameModel.getCellOwner(row + i, col + i) != currentPlayer) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }

        return false; // No win found in major diagonal
    }
    private boolean checkMinor() {
        int winThreshold = gameModel.getWinThreshold();
        int numberOfRows = gameModel.getNumberOfRows();
        int numberOfColumns = gameModel.getNumberOfColumns();

        // Loop through all possible starting points for a win in minor diagonal
        for (int row = 0; row <= numberOfRows - winThreshold; row++) {
            for (int col = winThreshold - 1; col < numberOfColumns; col++) {
                OXOPlayer currentPlayer = gameModel.getCellOwner(row, col);
                if (currentPlayer == null) continue; // Skip if cell is unclaimed

                boolean win = true;
                for (int i = 1; i < winThreshold; i++) {
                    if (gameModel.getCellOwner(row + i, col - i) != currentPlayer) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }

        return false; // No win found in minor diagonal
    }
    private boolean checkDraw(){
        for(int i =0; i<gameModel.getNumberOfRows(); i++){
            for(int j =0; j<gameModel.getNumberOfColumns(); j++) {
                if (gameModel.getCellOwner(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void increaseWinThreshold() {
        if(!gameModel.getIsAct()) {
            return;
        }
        int winNum = gameModel.getWinThreshold();
        if(winNum >= gameModel.getNumberOfRows() || winNum >= gameModel.getNumberOfColumns()){
            return;
        }
        gameModel.setWinThreshold(winNum + 1);
    }
    public void decreaseWinThreshold() {
        if(!gameModel.getIsAct() || isStart()){
            return;
        }
        int winNum = gameModel.getWinThreshold();
        if(winNum <= 3){
            return;
        }
        gameModel.setWinThreshold(winNum - 1);
    }

    public boolean isStart(){
        for(int i = 0; i< gameModel.getNumberOfRows(); i++){
            for(int j = 0; j< gameModel.getNumberOfColumns(); j++){
                if(gameModel.getCellOwner(i, j)!=null){
                    return true;
                }
            }
        }
        return false;
    }
    public void reset() {
        for(int i=0; i< gameModel.getNumberOfRows(); i++){
            for(int j = 0; j< gameModel.getNumberOfColumns(); j++){
                gameModel.setCellOwner(i, j, null);
            }
        }
        gameModel.setCurrentPlayerNumber(0);
        gameModel.setGameDrawn(false);
        gameModel.setWinner(null);
        gameModel.setAct(true);
    }
}

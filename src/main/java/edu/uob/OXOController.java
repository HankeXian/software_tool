package edu.uob;

public class OXOController {
    OXOModel gameModel;

    public OXOController(OXOModel model) {
        gameModel = model;
    }

    public void handleIncomingCommand(String command) throws OXOMoveException {
        int row = command.charAt(0)-'a';
        int col = command.charAt(1)-48-1;

        gameModel.setCellOwner(row, col, gameModel.getPlayerByNumber(gameModel.getCurrentPlayerNumber()));

        if(gameModel.getCurrentPlayerNumber()==1){
            gameModel.setCurrentPlayerNumber(0);
        }
        else{
            gameModel.setCurrentPlayerNumber(1);
        }
    }
    public void addRow() {
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
    public void increaseWinThreshold() {}
    public void decreaseWinThreshold() {}
    public void reset() {
        for(int i=0; i< gameModel.getNumberOfRows(); i++){
            for(int j = 0; j< gameModel.getNumberOfColumns(); j++){
                gameModel.setCellOwner(i, j, null);
            }
        }
        gameModel.setCurrentPlayerNumber(0);

    }
}

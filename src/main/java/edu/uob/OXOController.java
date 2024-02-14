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
    public void addRow() {}
    public void removeRow() {}
    public void addColumn() {}
    public void removeColumn() {}
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

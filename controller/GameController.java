package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
        this.model.resetOriginalMatrix();
        this.view.clearAllBoxFromPanel();
        this.view.initialGame(model.getMatrix());
        view.resetSteps(); // New method to reset steps and label
    }

    public boolean doMove(int row, int col, Direction direction) {
        int blockId = model.getId(row, col);
        if (blockId == 0) return false; // empty space

        // Determine block size based on ID
        int width = 1, height = 1;
        if (blockId == 2) { // Horizontal block (1×2)
            width = 2;
            height = 1;
        } else if (blockId == 3) { // Vertical block (2×1)
            width = 1;
            height = 2;
        } else if (blockId == 4) { // Big block (2×2)
            width = 2;
            height = 2;
        }

        // Check if movement is possible
        if (canMove(row, col, width, height, direction)) {
            // Clear old positions
            clearBlock(row, col, width, height);

            // Calculate new position
            int newRow = row + direction.getRow();
            int newCol = col + direction.getCol();

            // Set new positions
            placeBlock(newRow, newCol, width, height, blockId);

            // Update view
            updateBoxPosition(row, col, newRow, newCol);
            return true;
        }
        return false;
    }

    private void clearBlock(int row, int col, int width, int height) {
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                model.getMatrix()[r][c] = 0;
            }
        }
    }

    private void placeBlock(int row, int col, int width, int height, int blockId) {
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                model.getMatrix()[r][c] = blockId;
            }
        }
    }

    private void updateBoxPosition(int oldRow, int oldCol, int newRow, int newCol) {
        BoxComponent box = view.getSelectedBox();
        box.setRow(newRow);
        box.setCol(newCol);
        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2,
                box.getRow() * view.getGRID_SIZE() + 2);
        box.repaint();
    }

    private boolean canMove(int row, int col, int width, int height, Direction direction) {
        int newRow = row + direction.getRow();
        int newCol = col + direction.getCol();

        // Check boundaries
        if (newRow < 0 || newRow + height > model.getHeight() ||
                newCol < 0 || newCol + width > model.getWidth()) {
            return false;
        }

        // Check if all new positions are empty
        for (int r = newRow; r < newRow + height; r++) {
            for (int c = newCol; c < newCol + width; c++) {
                if (model.getId(r, c) != 0) {
                    // Allow movement if the occupied space is part of the same block
                    if (r < row || r >= row + height || c < col || c >= col + width) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //todo: add other methods such as loadGame, saveGame...

}


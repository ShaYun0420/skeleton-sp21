package game2048;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);  // 切换视角

        for (int col = 0; col < size(); ++col) {
            // 从 tile(col, row + 1) 遍历至 tile(col, size() - 1)
                // 若为空则跳过
                // 若非空则判断 cursor_tile 与 cur_tile 的值
                    // 若 cursor_tile.value() != cur_tile.value() 则 cur_tile 移动至 (col, cursor - 1)
                    // 若 cursor_tile.value() == cur_tile.value() 则
                    // 判断 cursor_tile 是否合并过
                        // 若 cursor_tile 合并过则 cur_tile 移动至 (col, cursor - 1)
                        // 若 cursor_tile 没有合并过则 cur_tile 移动至 (col, cursor)
                            // 合并后更新 score 和 changed
            // 记录每一行的 tile 是否合并过
            boolean[] hasMerged = new boolean[size()];
            Arrays.fill(hasMerged, false);

            for (int row = size() - 2; row >= 0; --row) {
                Tile cur_tile = tile(col, row);  // 获取当前 tile(col, row)
                if (cur_tile == null)  // 若为空则跳过
                    continue;

                // 向上寻找首个非空 tile
                int cursor;
                for (cursor = row + 1; cursor < size(); ++cursor) {
                    Tile cursor_tile = tile(col, cursor);
                    if (cursor_tile == null)
                        continue;

                    // 若非空则判断 cursor_tile 与 cur_tile 的值
                    if (cursor_tile.value() == cur_tile.value()) {
                        // 判断是否合并过
                        if (hasMerged[cursor]) {                        // 不合并，移动至 (col, cursor - 1)
                            board.move(col, cursor - 1, cur_tile);
                            changed = rowChange(row, cursor - 1);
                        } else {                                        // 合并至 (col, cursor)
                            board.move(col, cursor, cur_tile);
                            hasMerged[cursor] = true;
                            score += tile(col, cursor).value();
                            changed = rowChange(row, cursor);
                        }
                    } else {                                            // 只移动不合并
                        board.move(col, cursor - 1, cur_tile);
                        changed = rowChange(row, cursor - 1);
                    }
                    // 至此，已经找到首个非空的 tile 并进行了相关操作
                    break;
                }

                // 若 cur_tile 上方全为空
                if (tile(col, cursor) == null) {
                    board.move(col, cursor, cur_tile);
                    changed = rowChange(row, cursor);
                }
            }
        }

        board.setViewingPerspective(Side.NORTH);  // 切换为标准视角

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if rows of one tile is equal to another. */
    private boolean rowChange(int row1, int row2) {
        return row1 != row2;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        // 遍历 b 上的每个 tile 若 tile 为 null 表示该位置为空
//        Iterator<Tile> iter = b.iterator();
        for (Tile t : b) {
            if (t == null)
                return true;
        }
//        for (int row = 0; row < b.size(); ++row) {
//            for (int col = 0; col < b.size(); ++col) {
//                if (b.tile(col, row) == null)
//                    return true;
//            }
//        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (Tile t : b) {
            if (t != null && t.value() == MAX_PIECE)
                return true;
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b))
            return true;
        for (int row = 0; row < b.size(); ++row) {
            for (int col = 0; col < b.size(); ++col) {
                Tile cur = b.tile(col, row);  // 当前 tile

                // 获取 tile 的四个方位的邻居, 若邻居节点的 col 和 row 坐标均不越界则为有效邻居
                if (col - 1 >= 0) {
                    Tile left = b.tile(col - 1, row);
                    if (cur.value() == left.value())
                        return true;
                }
                if (col + 1 < b.size()) {
                    Tile right = b.tile(col + 1, row);
                    if (cur.value() == right.value())
                        return true;
                }
                if (row + 1 < b.size()) {
                    Tile above = b.tile(col, row + 1);
                    if (cur.value() == above.value())
                        return true;
                }
                if (row - 1 >= 0) {
                    Tile below = b.tile(col, row - 1);
                    if (cur.value() == below.value())
                        return true;
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}

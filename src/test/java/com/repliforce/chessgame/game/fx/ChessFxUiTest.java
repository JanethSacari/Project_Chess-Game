package com.repliforce.chessgame.game.fx;

import com.repliforce.chessgame.chess.ChessMatch;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChessFxUiTest {

    @BeforeAll
    static void startJavaFx() throws InterruptedException {
        CountDownLatch started = new CountDownLatch(1);
        Platform.startup(() -> {
            Platform.setImplicitExit(false);
            started.countDown();
        });
        assertTrue(started.await(10, TimeUnit.SECONDS), "JavaFX toolkit did not start");
    }

    @AfterAll
    static void stopJavaFx() {
        Platform.exit();
    }

    @Test
    void boardViewRendersPiecesAndWiresSquareAndNewGameActions() throws Exception {
        runOnJavaFxThread(() -> {
            AtomicInteger clickedRow = new AtomicInteger(-1);
            AtomicInteger clickedColumn = new AtomicInteger(-1);
            AtomicInteger newGameCount = new AtomicInteger();
            ChessBoardView view = new ChessBoardView(
                    (row, column) -> {
                        clickedRow.set(row);
                        clickedColumn.set(column);
                    },
                    newGameCount::incrementAndGet);
            view.render(new ChessMatch().getPieces(), new boolean[8][8], -1, -1);

            GridPane board = (GridPane) view.getCenter();
            assertEquals(64, board.getChildren().size());
            assertEquals("P", squareAt(board, 6, 4).getText());
            assertTrue(squareAt(board, 0, 0).getStyle().contains("#f0d9b5"));
            assertTrue(squareAt(board, 0, 1).getStyle().contains("#b58863"));

            squareAt(board, 6, 4).fire();
            assertEquals(6, clickedRow.get());
            assertEquals(4, clickedColumn.get());

            HBox footer = (HBox) view.getBottom();
            Button newGameButton = (Button) footer.getChildren().get(1);
            newGameButton.fire();
            assertEquals(1, newGameCount.get());
        });
    }

    @Test
    void controllerSelectsAndMovesPiecesThroughBoardButtons() throws Exception {
        runOnJavaFxThread(() -> {
            ChessGameController controller = new ChessGameController();
            ChessBoardView view = controller.getView();
            GridPane board = (GridPane) view.getCenter();

            squareAt(board, 6, 4).fire();
            assertTrue(squareAt(board, 4, 4).getStyle().contains("#a9c66e"));

            squareAt(board, 4, 4).fire();
            assertEquals("P", squareAt(board, 4, 4).getText());
            assertEquals("", squareAt(board, 6, 4).getText());
            assertEquals("Turno 2 | Vez das vermelhas", statusLabel(view).getText());
        });
    }

    @Test
    void controllerShowsInvalidSelectionAndKeepsCurrentTurn() throws Exception {
        runOnJavaFxThread(() -> {
            ChessGameController controller = new ChessGameController();
            ChessBoardView view = controller.getView();

            squareAt((GridPane) view.getCenter(), 1, 0).fire();

            assertTrue(statusLabel(view).getText().contains("The chosen piece is not yours!"));
            assertTrue(statusLabel(view).getText().contains("Turno 1 | Vez das azuis"));
        });
    }

    @Test
    void controllerTracksCapturesAndStartsANewGame() throws Exception {
        runOnJavaFxThread(() -> {
            ChessGameController controller = new ChessGameController();
            ChessBoardView view = controller.getView();
            GridPane board = (GridPane) view.getCenter();

            squareAt(board, 6, 4).fire();
            squareAt(board, 4, 4).fire();
            squareAt(board, 1, 3).fire();
            squareAt(board, 3, 3).fire();
            squareAt(board, 4, 4).fire();
            squareAt(board, 3, 3).fire();

            Label capturedPieces = (Label) ((HBox) view.getBottom()).getChildren().get(0);
            assertTrue(capturedPieces.getText().contains("Vermelhas: [P]"));

            Button newGameButton = (Button) ((HBox) view.getBottom()).getChildren().get(1);
            newGameButton.fire();

            assertEquals("Turno 1 | Vez das azuis", statusLabel(view).getText());
            assertTrue(capturedPieces.getText().contains("Azuis: []   Vermelhas: []"));
            assertEquals("P", squareAt(board, 6, 4).getText());
            assertEquals("P", squareAt(board, 1, 3).getText());
        });
    }

    @Test
    void applicationCreatesAndShowsTheGameWindow() throws Exception {
        runOnJavaFxThread(() -> {
            ChessFxApplication application = new ChessFxApplication();
            Stage stage = new Stage();
            application.start(stage);

            assertEquals("Chess Game", stage.getTitle());
            assertTrue(stage.isShowing());
            assertInstanceOf(ChessBoardView.class, stage.getScene().getRoot());

            stage.close();
        });
    }

    private static Label statusLabel(ChessBoardView view) {
        VBox header = (VBox) view.getTop();
        return (Label) header.getChildren().get(1);
    }

    private static Button squareAt(GridPane board, int row, int column) {
        for (Node node : board.getChildren()) {
            int nodeRow = GridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node);
            int nodeColumn = GridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node);
            if (nodeRow == row && nodeColumn == column) {
                return (Button) node;
            }
        }
        throw new AssertionError("No square at row " + row + ", column " + column);
    }

    private static void runOnJavaFxThread(Runnable action) throws Exception {
        FutureTask<Void> task = new FutureTask<>(action, null);
        Platform.runLater(task);
        task.get(10, TimeUnit.SECONDS);
    }
}

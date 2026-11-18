package com.repliforce.chessgame.game.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessFxApplication extends Application {

    @Override
    public void start(Stage stage) {
        ChessGameController controller = new ChessGameController();
        Scene scene = new Scene(controller.getView());
        stage.setTitle("Chess Game");
        stage.setMinWidth(760);
        stage.setMinHeight(720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

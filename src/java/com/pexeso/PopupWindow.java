package com.pexeso;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindow {

    public static void display(Player[] players) {

        Stage window = new Stage();
        // while this window is on the screen, you cannot interact with other windows.
        window.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(30);
        Label messageLabel = new Label();

        // get winner
        int maxPoint = players[0].getPoints().size();
        ArrayList<Player> winner = new ArrayList<>(List.of(players[0]));
        for(int i=1; i<players.length; i++){
            int playerPoint = players[i].getPoints().size();
            if(playerPoint>maxPoint) {
                maxPoint=playerPoint;
                winner.clear();
                winner.add(players[i]);
            }else if(playerPoint==maxPoint){
                winner.add(players[i]);
            }
        }
        // set popup window title
        if(winner.size()==1){
            window.setTitle(winner.get(0).getName() + " won!");
        }else{
            window.setTitle("Draw");
        }

        // set popup window content
        StringBuilder message = new StringBuilder();
        for(Player player: players){
            message.append(player.getName() + ":   " + player.getPoints().size()).append("\n");
        }

        messageLabel.setText(message.toString());
        messageLabel.setStyle("-fx-text-fill: black; -fx-font-size: 20;");

        Button okButton = new Button();
        okButton.setText("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });

        layout.getChildren().add(messageLabel);
        layout.getChildren().add(okButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(PopupWindow.class.getResource("/com/pexeso/css/styles.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }
}

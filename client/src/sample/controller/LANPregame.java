package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.model.Dice;
import sample.model.Players;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;

public class LANPregame implements Initializable {
    public AnchorPane preScene;
    public Button rollButton;
    public GridPane boardOfOrder;
    public Button startButton;
    public ImageView diceDisplay;

    private ResourceBundle bundle;

    private ArrayList <Players> listOfPlayers = new ArrayList<>();

    void setGameLanguage(ResourceBundle bundle){

        this.bundle = bundle;
    }




    private void displayRank() {
        boardOfOrder.setOpacity(1);
        boardOfOrder.add(new Label(bundle.getString("order")), 0, 0 );
        boardOfOrder.setAlignment(Pos.CENTER);

        boardOfOrder.add(new Label(bundle.getString("player")), 1, 0);

        for (int i = 0; i < listOfPlayers.size(); i++){
            boardOfOrder.addColumn(0, new Label(Integer.toString(i+1)));
            boardOfOrder.addColumn(1, new Label(listOfPlayers.get(i).getName()));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rollButton.setDisable(true);
        startButton.setDisable(true);
    }
}
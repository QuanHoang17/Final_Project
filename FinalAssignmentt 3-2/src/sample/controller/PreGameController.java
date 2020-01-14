package sample.controller;


import javafx.fxml.Initializable;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import sample.model.Players;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.ResourceBundle;

public class PreGameController implements Initializable {

    public Label rollMsg;
    public ImageView diceDisplay;
    public AnchorPane preScene;
    public Label rollNext;
    public Button rollButton;
    public Button startButton;
    public GridPane boardOfOrder;
    public Label textMsg;

    private ResourceBundle bundle;


    private ArrayList <Players> listOfPlayers = new ArrayList<>();



    void setGameLanguage(ResourceBundle bundle){
        this.bundle = bundle;
    }

    private void setNodeLanguage(){

        rollButton.setText(bundle.getString("roll"));

        startButton.setText(bundle.getString("start"));

        textMsg.setText(bundle.getString("information_Prescene"));

    }

    void setNumOfPlayers(ArrayList<Players> listOfPlayers){
        this.listOfPlayers = listOfPlayers;
    }



    //Hue
    private void displayRank() {
        boardOfOrder.setOpacity(1);
        boardOfOrder.add(new Label(bundle.getString("order")), 0, 0 );
        boardOfOrder.setAlignment(Pos.CENTER);
        boardOfOrder.add(new Label(bundle.getString("player")), 1, 0);

        //Sort the list of players in descending order by their order.
        listOfPlayers.sort(Comparator.comparing(Players::getOrder).reversed());

        for (int i = 0; i < listOfPlayers.size(); i++){
            boardOfOrder.addColumn(0, new Label(Integer.toString(i+1)));
            boardOfOrder.addColumn(1, new Label(listOfPlayers.get(i).getName()));
        }
    }


}

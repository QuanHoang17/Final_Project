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
    // private Socket socket = new Socket("10.247.194.193",5000);
    private Socket socket = new Socket("10.247.194.193",5000);
    private Dice dice = new Dice();
    private boolean isRoll;

    private ArrayList <Players> listOfPlayers = new ArrayList<>();

    public LANPregame() throws IOException {
        new Thread(()->{
            try {
                DataInputStream fromServer = new DataInputStream(socket.getInputStream());


                isRoll = fromServer.readBoolean();
                if (isRoll){
                    rollButton.setDisable(false);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    void setGameLanguage(ResourceBundle bundle){

        this.bundle = bundle;
    }

    public void rollOrder(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        DataInputStream fromServer = new DataInputStream(socket.getInputStream());

        int diceValFromSer = fromServer.readInt();
        System.out.println(diceValFromSer);
        int diceValOfClient = dice.roll_order_LAN(diceValFromSer);
        diceDisplay.setImage(new Image("file:src/sample/view/img/Facedice/"+ diceValOfClient + ".png"));
        System.out.println("order" + diceValOfClient);


        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
        toServer.writeInt(diceValOfClient);

        int numOfClients = fromServer.readInt();


        do {

            ObjectInputStream objFromServer = new ObjectInputStream(socket.getInputStream());

            DataOutputStream signal = new DataOutputStream(socket.getOutputStream());
            Players temp = (Players) objFromServer.readObject();

            listOfPlayers.add(temp);
            signal.writeBoolean(true);


        } while (listOfPlayers.size() != (numOfClients + 1));

        displayRank();
        startButton.setDisable(false);

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
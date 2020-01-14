
package sample.controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import sample.model.Dice;
import sample.model.Players;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class LANPregame implements Initializable {
    public GridPane boardOfOrder;
    public Button startButton;
    public Label rollMsg;
    public Label rollNext;
    public Button rollButton;
    @FXML
    public ImageView diceDisplay;
    private ServerSocket server  = new ServerSocket(5000);
    private Socket socket;
    private ArrayList <Players> listOfPlayers = new ArrayList<>();
    private Dice dice = new Dice();

    private ArrayList<Socket> listOfSocket = new ArrayList<>();
    private int numOfClients = 0;
    private int diceValueGetFromClients = 0;
    private boolean isRolled = false;


    void setListOfPlayers(ArrayList<Players> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public LANPregame() throws IOException {
        new Thread(()->{

            while (true){
                try {
                    socket = server.accept();

                    numOfClients++;

                    listOfSocket.add(socket);

                    if (numOfClients +1 == listOfPlayers.size()){
                        Platform.runLater(() ->{
                            rollButton.setDisable(false);
                            rollMsg.setOpacity(0);
                            rollNext.setText("Click to Roll For Your Order");
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }

    private void setOrderForClient(int diceValueGetFromClients, String ip) {
        for (int i = 1; i < listOfPlayers.size(); i++){

            if (listOfPlayers.get(i).getIpAddress().equals(ip)){
                listOfPlayers.get(i).setOrder(diceValueGetFromClients);
                break;
            }
        }
    }

    private void sendSignalsForClientToRoll  () throws IOException {

        int index = 0;
        while(index < listOfSocket.size()){

            DataOutputStream toClients = new DataOutputStream(listOfSocket.get(index).getOutputStream());
            toClients.writeBoolean(true);
            toClients.writeInt(diceValueGetFromClients);

            while (true){

                DataInputStream fromClients = new DataInputStream(listOfSocket.get(index).getInputStream());

                int diceVal = fromClients.readInt();

                if (diceVal != diceValueGetFromClients){

                    InetAddress ip = socket.getInetAddress();


                    rollMsg.setText(getNameFromIp(ip.getHostAddress()) + " Has Rolled");
                    rollMsg.setOpacity(1);

                    //Display image of the dice based on the value of diceVal.
                    diceDisplay.setImage(new Image("file:src/sample/view/Facedice/" + diceVal + ".png"));

                    setOrderForClient(diceVal, ip.getHostAddress());

                    diceValueGetFromClients = diceVal;
                    break;
                }
            }
            index++;

        }
    }

    private void sendListOfPlayersToClients() throws IOException {
        DataOutputStream sendNumOfClients = new DataOutputStream(socket.getOutputStream());
        sendNumOfClients.writeInt(numOfClients);

        int i = 0;
        while(i < listOfSocket.size()){

            int x = 0;

            while (x < listOfPlayers.size()){
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(listOfSocket.get(i).getOutputStream());

                objectOutputStream.writeObject(listOfPlayers.get(x));
                boolean flag = false;

                while (true){
                    DataInputStream fromServer = new DataInputStream(listOfSocket.get(i).getInputStream());

                    flag = fromServer.readBoolean();
                    if (flag){
                        x++;
                        break;
                    }
                }
            }
            i++;
        }
    }

    public void rollOrder(ActionEvent actionEvent) throws IOException {

        int order = dice.roll_order();

        listOfPlayers.get(0).setOrder(order);
        diceValueGetFromClients = order;

       //isRolled = true;
        sendSignalsForClientToRoll();

        rollNext.setOpacity(0);

        displayRank();

       sendListOfPlayersToClients();

       startButton.setDisable(false);
    }

    private String getNameFromIp(String hostAddress) {
        for (int i = 1; i < listOfPlayers.size(); i++){
            if (listOfPlayers.get(i).getIpAddress().equals(hostAddress)){
                return listOfPlayers.get(i).getName();
            }
        }
        return null;
    }

    private void displayRank() {
        //rollNext.setOpacity(0);
        boardOfOrder.setOpacity(1);
        boardOfOrder.add(new Label("order"), 0, 0 );
        boardOfOrder.setAlignment(Pos.CENTER);

        boardOfOrder.add(new Label("player"), 1, 0);

        //Sort the list of players in descending order by their order.
        listOfPlayers.sort(Comparator.comparing(Players::getOrder).reversed());

        for (int i = 0; i < listOfPlayers.size(); i++){
            boardOfOrder.addColumn(0, new Label(Integer.toString(i+1)));
            boardOfOrder.addColumn(1, new Label(listOfPlayers.get(i).getName()));
        }
    }

    public void start(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rollButton.setDisable(true);
        startButton.setDisable(true);
        rollMsg.setText("Wait For Others To Join");

    }


}

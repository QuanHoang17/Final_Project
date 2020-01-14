/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 24/12/2019
  Last modified: 14/1/2020
  By: Hoang Minh Quan (s3754450), Dang Truong Nguyen Long(s3757333), Nguyen Minh Nhat(s3740975), Pham Huynh Ngoc Hue(s3702554)
  Acknowledgement:
  Thanks to Mr. Quang, his slides and lectures, we were able to design this program as expected from the specifications.
In addition, for more advanced features, we also did some researches from : https://docs.oracle.com/en/.
Our images and sounds used for UI were mainly retrieved from: https://www.freepik.com and https://freesound.org/browse/tags/sound-effects/
For enhancing the game UI, we apply some CSS knowledge from https://www.w3schools.com/css/default.asp.
*/

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/BeginScene.fxml"));
        Parent root = loader.load();


        primaryStage.setTitle("The Pachisi Game Board");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

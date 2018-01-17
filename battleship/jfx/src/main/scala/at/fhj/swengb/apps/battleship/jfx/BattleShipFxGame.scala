package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene


class BattleShipFxGame extends Initializable {


 @FXML def giveUp(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadWelcome,BattleShipFxApp.loadMain)
 }


 override def initialize(location: URL, resources: ResourceBundle): Unit = {

 }





}
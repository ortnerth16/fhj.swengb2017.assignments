package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene


class BattleShipFxEditGame extends Initializable {

 @FXML def startGame(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadGame,BattleShipFxApp.loadMain)
 }

 @FXML def back(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadNewGame,BattleShipFxApp.loadMain)
 }


 override def initialize(location: URL, resources: ResourceBundle): Unit = {

 }





}
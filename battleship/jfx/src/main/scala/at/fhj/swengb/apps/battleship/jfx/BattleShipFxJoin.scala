package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene


class BattleShipFxJoin extends Initializable {

 @FXML def next(): Unit = {
  //BattleShipFxApp.display(BattleShipFxApp.loadEditGame,BattleShipFxApp.loadMain)
 }

 @FXML def back(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadWelcome,BattleShipFxApp.loadMain)
 }


 override def initialize(location: URL, resources: ResourceBundle): Unit = {

 }





}
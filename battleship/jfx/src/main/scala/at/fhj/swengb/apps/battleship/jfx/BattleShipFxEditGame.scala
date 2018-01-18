package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene
import javafx.scene.control.{Label, TextArea}
import javafx.scene.control.TextField

import at.fhj.swengb.apps.battleship.model.GameRound


class BattleShipFxEditGame extends Initializable {






 // game config
 // **************************************************************
 val list1: List[String] = List("item11", "item12", "item13")
 val list2: List[String] = List("item21", "item32", "item33")
 val list3: List[String] = List("item31", "item32", "item33")
 // **************************************************************

 // game round is created at "create game"
 var game: GameRound = _

 @FXML private var gameName: TextField = _
 @FXML private var playerA: TextField = _
 @FXML private var playerB: TextField = _

 @FXML
 def back(): Unit = backToHome()
 def startGame(): Unit = createGame()


 override def initialize(location: URL, resources: ResourceBundle): Unit = {

 }

def createGame(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadGame,BattleShipFxApp.loadMain)
 }
def backToHome(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadNewGame,BattleShipFxApp.loadMain)
 }



}
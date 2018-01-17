package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.nio.file.{Files, Paths}
import java.util.{Calendar, ResourceBundle}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.ListView
import javafx.scene.control.TextArea

import at.fhj.swengb.apps.battleship.BattleShipProtobuf
import at.fhj.swengb.apps.battleship.BattleShipProtocol.convert
import at.fhj.swengb.apps.battleship.model.{BattleField, BattleShipGame, Fleet, FleetConfig}

class EditModeController extends Initializable {

  // create a new game

  var battleshipGame: BattleShipGame = _


  /**
    * A text area box to place the history of the game
    */
  @FXML private var shipList = ListView
  //@FXML private var verticalORhorizontal = _
  @FXML private var startPosX = TextArea
  @FXML private var startPosY = TextArea


  // needed for the size of the battlefield
  //private def getCellHeight(y: Int): Double = battleGroundGridPane.getRowConstraints.get(y).getPrefHeight
  //private def getCellWidth(x: Int): Double = battleGroundGridPane.getColumnConstraints.get(x).getPrefWidth

  @FXML
  def deleteShip(): Unit = deleteShipFromBattlefield()



  def placeShip(): Unit = placeShipToField()



  def back(): Unit = backToHome()



  def startGame(): Unit = beginGame()

  def initGame(): Unit = ???

  override def initialize(url: URL, rb: ResourceBundle): Unit = initGame()

  //def appendLog(message: String): Unit = log.appendText(message + "\n")


  def backToHome(): Unit = {

  }

  /**
    * delete the selected ship from list
    */
  def deleteShipFromBattlefield(): Unit = {

  }

  /**
    * place the ship
    */
  def placeShipToField(): Unit = {

  }


  /**
    * begin the game
    */
  def beginGame(): Unit = {
    // check all preconditions
  }

}

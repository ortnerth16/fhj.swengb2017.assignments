package at.fhj.swengb.apps.battleship.jfx

import javafx.scene.control.TextArea
import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene
import javafx.scene.layout.GridPane

import at.fhj.swengb.apps.battleship.model._


class BattleShipFxGame extends Initializable {


 @FXML
 private var ownGridPane: GridPane = _

 @FXML
 private var enemyGridPane: GridPane = _

 @FXML
 private var log: TextArea = _

 @FXML
 def giveUp(): Unit = {
  BattleShipFxApp.display(BattleShipFxApp.loadWelcome,BattleShipFxApp.loadMain)
 }

 override def initialize(location: URL, resources: ResourceBundle): Unit = initGame()

 private def getCellHeight(y: Int): Double = {
  ownGridPane.getRowConstraints.get(y).getPrefHeight
  enemyGridPane.getRowConstraints.get(y).getPrefHeight
 }

 private def getCellWidth(x: Int): Double = {
  ownGridPane.getColumnConstraints.get(x).getPrefWidth
  enemyGridPane.getColumnConstraints.get(x).getPrefWidth
 }

 def appendLog(message: String): Unit = log.appendText(message + "\n")

 private var gameRound: GameRound = _
 private var filename: String = _


 //TODO Playernames, Battlename

 /**
   * Create a new game.
   *
   * This means
   *
   * - resetting all cells to 'empty' state
   * - placing your ships at random on the battleground
   *
   */
 /*def init(game: BattleShipGame): Unit = {
   battleshipGame = game
   battleGroundGridPane.getChildren.clear()
   for (c <- game.getCells) {
     battleGroundGridPane.add(c, c.pos.x, c.pos.y)
   }
   game.getCells().foreach(c => c.init)
 }*/

 def init(game: GameRound): Unit = {
  gameRound = game
  ownGridPane.getChildren.clear()
  for (c <- game.getGameA.getCells) {
   ownGridPane.add(c, c.pos.x, c.pos.y)
  }
  game.getGameA.getCells().foreach(c => c.init)

  enemyGridPane.getChildren.clear()
  for (c <- game.getGameB.getCells) {
   enemyGridPane.add(c, c.pos.x, c.pos.y)
  }
  game.getGameB.getCells().foreach(c => c.init)
 }


 private def initGame(): Unit = {
  val field = BattleField(10, 10, Fleet(FleetConfig.Standard))

  val battlefield: BattleField = BattleField.placeRandomly(field)
  val gameA = BattleShipGame(battlefield, getCellWidth, getCellHeight, appendLog, "PlayerA")
  val gameB = BattleShipGame(battlefield, getCellWidth, getCellHeight, appendLog, "PlayerB")

  val game: GameRound = GameRound("PlayerA", "PlayerB", "Battle of Bearstards", appendLog,gameA, gameB)
  init(game)
  appendLog("New game started.")
 }





}
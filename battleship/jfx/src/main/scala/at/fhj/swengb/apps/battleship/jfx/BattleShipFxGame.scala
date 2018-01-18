package at.fhj.swengb.apps.battleship.jfx

import javafx.scene.control.TextArea
import java.net.URL
import java.nio.file.{Files, Paths}
import java.util.{Calendar, ResourceBundle}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.text.Text

import at.fhj.swengb.apps.battleship.BattleShipProtobuf
import at.fhj.swengb.apps.battleship.BattleShipProtocol.convert
import at.fhj.swengb.apps.battleship.model._


class BattleShipFxGame extends Initializable {


 @FXML
 private var ownGridPane: GridPane = _

 @FXML
 private var enemyGridPane: GridPane = _

 @FXML
 private var log: TextArea = _

 @FXML
 private var headline: Text = _

 @FXML
 private var playerTurn: Text = _

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


 def save(): Unit = saveGameState()

 def load(): Unit = loadGameState()

 /**
   * Create a new game.
   *
   * This means
   *
   * - resetting all cells to 'empty' state
   * - placing your ships at random on the battleground
   *
   */

 def init(game: GameRound): Unit = {
  gameRound = game
  setLabels()

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
  val playerA = "PolarBear Miriam"
  val playerB = "PolarBear Thomas"

  val field = BattleField(10, 10, Fleet(FleetConfig.Standard))

  val battlefield: BattleField = BattleField.placeRandomly(field)
  val gameA = BattleShipGame(battlefield, getCellWidth, getCellHeight, appendLog, playerA)
  val gameB = BattleShipGame(battlefield, getCellWidth, getCellHeight, appendLog, playerB)

  val game: GameRound = GameRound(playerA, playerB, "Battle of Bearstards", appendLog,gameA, gameB, 2, playerA)
  init(game)
  appendLog("New game started.")
 }

 def setLabels(): Unit = {
  headline.setText(gameRound.playerA ++ " " ++ "@" ++ gameRound.gameName ++ " " ++ gameRound.playerB)

  if(gameRound.playerB.takeRight(1) != "s")
   playerTurn.setText(gameRound.playerA ++ "'" ++ "s" ++ " " ++ "turn")
  else
   playerTurn.setText(gameRound.playerB ++ "'" ++ " " ++ "turn")
 }

 def saveGameState(): Unit = {
  val datetime = Calendar.getInstance().getTime
  val test = datetime.toString.filterNot(x => x.isWhitespace ||  x.equals(':'))
  filename = "battleship"
  convert(gameRound).writeTo(Files.newOutputStream(Paths.get("battleship/"+filename+".bin")))

  appendLog("Saved the game")

 }

 def loadGameState(): Unit = {
  val reload = BattleShipProtobuf.Game.parseFrom(Files.newInputStream(Paths.get("battleship/"+filename+".bin")))

  val gameWithOldValues = GameRound(gameRound.playerA,
   gameRound.playerB,
   gameRound.gameName,
   appendLog,
   convert(reload).getGameA,
   convert(reload).getGameA,
   2,
   gameRound.currentPlayer)

  gameWithOldValues.getGameA.gameState = convert(reload).getGameA.gameState
  //gameWithOldValues.battleShipGameB.gameState = convert(reload).battleShipGameB.gameState
  init(gameWithOldValues)
  gameWithOldValues.getGameA.update(gameRound.getGameA.gameState.length)
  //gameWithOldValues.battleShipGameB.update(gameRound.battleShipGameB.gameState.length)
  appendLog("Loaded the game")
 }

}
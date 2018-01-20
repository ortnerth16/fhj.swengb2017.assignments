package at.fhj.swengb.apps.battleship.jfx

import javafx.scene.control.TextArea
import java.net.URL
import java.nio.file.{Files, Paths}
import java.util.{Calendar, ResourceBundle}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.layout.GridPane
import javafx.scene.text.Text

import at.fhj.swengb.apps.battleship.BattleShipProtocol.convert
import at.fhj.swengb.apps.battleship.jfx.BattleShipFxApp.gameRound
import at.fhj.swengb.apps.battleship.model._


class BattleShipFxGame extends Initializable {


  @FXML private var ownGridPane: GridPane = _

  @FXML private var enemyGridPane: GridPane = _

  @FXML private var log: TextArea = _

  @FXML private var headline: Text = _

  @FXML private var playerTurn: Text = _

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
  private var fileName: String = BattleShipFxApp.getFilename()

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

  // TODO Change between players
  // TODO fix load bug
  // TODO load correct playerNames
  // TODO TimeSheet nachholen

  def init(game: GameRound): Unit = {

    setLabels()

    ownGridPane.getChildren.clear()
    for (c <- game.battleShipGameA.getCells) {
      ownGridPane.add(c, c.pos.x, c.pos.y)
    }
    game.battleShipGameA.getCells().foreach(c => c.init)

    enemyGridPane.getChildren.clear()
    for (c <- game.battleShipGameB.getCells) {
      enemyGridPane.add(c, c.pos.x, c.pos.y)
    }
    game.battleShipGameB.getCells().foreach(c => c.init)

  }


  private def initGame(): Unit = {
    if(BattleShipFxApp.getGameRound() != null) {

      gameRound = BattleShipFxApp.getGameRound()

      /*val playerA = "lsfdhd"
    val playerB = "ydlfjds"
    val gameName = "jfdlöh"*/


      init(gameRound)
      appendLog("New game started.")
    }
  }

  def setLabels(): Unit = {
    headline.setText(gameRound.playerA ++ " " ++ "@" ++ gameRound.gameName ++ " " ++ gameRound.playerB)

    if(gameRound.currentPlayer.takeRight(1) != "s")
      playerTurn.setText(gameRound.currentPlayer ++ "'" ++ "s" ++ " " ++ "turn")
    else
      playerTurn.setText(gameRound.currentPlayer ++ "'" ++ " " ++ "turn")
  }

  /*def saveGameState(): Unit = {
   val datetime = Calendar.getInstance().getTime
   val test = datetime.toString.filterNot(x => x.isWhitespace ||  x.equals(':'))
   filename = "battleship"
   convert(gameRound).writeTo(Files.newOutputStream(Paths.get("battleship/"+filename+".bin")))

   appendLog("Saved the game")

  }

  def loadGameState(): Unit = {
   val reload = BattleShipProtobuf.Game.parseFrom(Files.newInputStream(Paths.get("battleship/battleship.bin")))

   val gameWithOldValues = GameRound(convert(reload).playerA,
     convert(reload).playerB,
     convert(reload).gameName,
     appendLog,
     convert(reload).getGameA,
     convert(reload).getGameA,
     convert(reload).numberCurrentPlayers,
     convert(reload).currentPlayer)

   gameWithOldValues.getGameA.gameState = convert(reload).getGameA.gameState
   gameWithOldValues.battleShipGameB.gameState = convert(reload).battleShipGameB.gameState
   init(gameWithOldValues)
   gameWithOldValues.getGameA.update(gameRound.getGameA.gameState.length)
   gameWithOldValues.battleShipGameB.update(gameRound.battleShipGameB.gameState.length)
   appendLog("Loaded the game")
  }*/

  def saveGameState(): Unit = {
    BattleShipFxApp.saveGameState(fileName)
    convert(gameRound).writeTo(Files.newOutputStream(Paths.get("battleship/"+fileName+".bin")))
    appendLog("Saved the game")

  }

  def loadGameState(): Unit = {
    val gameWithOldValues = BattleShipFxApp.loadGameState(fileName)

    init(gameWithOldValues)
    gameWithOldValues.battleShipGameA.update(gameRound.battleShipGameA.gameState.length)
    gameWithOldValues.battleShipGameB.update(gameRound.battleShipGameB.gameState.length)
    appendLog("Loaded the game")
  }
}
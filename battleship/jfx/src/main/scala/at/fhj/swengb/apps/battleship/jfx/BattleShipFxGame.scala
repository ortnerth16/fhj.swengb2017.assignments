package at.fhj.swengb.apps.battleship.jfx

import javafx.scene.control.TextArea
import java.net.URL
import java.nio.file.{Files, Paths}
import java.util.{Calendar, ResourceBundle}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.layout.GridPane
import javafx.scene.text.Text

import at.fhj.swengb.apps.battleship.BattleShipProtobuf
import at.fhj.swengb.apps.battleship.BattleShipProtocol.convert
import at.fhj.swengb.apps.battleship.jfx.BattleShipFxApp.{filename, gameRound}
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
  private val fileName: String = BattleShipFxApp.getFilename()
  /*private var newBsGameA: BattleShipGame = _
  private var newBsGameB: BattleShipGame = _*/

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
  // TODO Hide enemy battlefield
  // TODO Implement GameState
  // TODO Display battlefields in correct place (different game instances)

  def init(game: GameRound): Unit = {

    setLabels()
    val newBsGameA = game.battleShipGameA.copy(getCellWidth = this.getCellWidth, getCellHeight = this.getCellHeight)
    val newBsGameB = game.battleShipGameB.copy(getCellWidth = this.getCellWidth, getCellHeight = this.getCellHeight)

    BattleShipFxApp.setGameRound(game.copy(battleShipGameA = newBsGameA, battleShipGameB = newBsGameB))
    gameRound = BattleShipFxApp.getGameRound()

    ownGridPane.getChildren.clear()
    for (c <- newBsGameA.getCells) {
      ownGridPane.add(c, c.pos.x, c.pos.y)
    }
    newBsGameA.getCells().foreach(c => c.init)


    enemyGridPane.getChildren.clear()
    for (c <- newBsGameB.getCells) {
      enemyGridPane.add(c, c.pos.x, c.pos.y)
    }
    newBsGameB.getCells().foreach(c => c.init)

  }

  def initAfterReload(game: GameRound): Unit = {

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

      init(gameRound)
      appendLog("New game started.")
    }
  }

  def setLabels(): Unit = {
    headline.setText(gameRound.playerA ++ " " ++ "@" ++ gameRound.gameName ++ " " ++ "vs" ++ " " ++ gameRound.playerB)

    if(gameRound.getCurrentPlayer.takeRight(1) != "s")
      playerTurn.setText(gameRound.getCurrentPlayer ++ "'" ++ "s" ++ " " ++ "turn")
    else
      playerTurn.setText(gameRound.getCurrentPlayer ++ "'" ++ " " ++ "turn")
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
    if(gameRound.getCurrentPlayer == gameRound.playerA)
      gameRound.setCurrentPlayer(gameRound.playerB)
    else gameRound.setCurrentPlayer(gameRound.playerA)
    setLabels()

    BattleShipFxApp.saveGameState(fileName)
    appendLog("Saved the game")

  }

  def loadGameState(): Unit = {

    val reload = BattleShipProtobuf.Game.parseFrom(Files.newInputStream(Paths.get(filename)))

    val gameWithOldValues = GameRound(convert(reload).playerA,
      convert(reload).playerB,
      convert(reload).gameName,
      x=>(),
      convert(reload).battleShipGameA,
      convert(reload).battleShipGameB)

    val newGameOldValues = gameWithOldValues.copy(battleShipGameA = gameWithOldValues.battleShipGameA.copy(getCellWidth = this.getCellWidth, getCellHeight = this.getCellHeight),
      battleShipGameB = gameWithOldValues.battleShipGameB.copy(getCellWidth = this.getCellWidth, getCellHeight = this.getCellHeight))

    newGameOldValues.battleShipGameA.gameState = convert(reload).battleShipGameA.gameState
    newGameOldValues.battleShipGameB.gameState = convert(reload).battleShipGameB.gameState

    initAfterReload(newGameOldValues)
    newGameOldValues.battleShipGameA.update(gameRound.battleShipGameA.gameState.length)
    newGameOldValues.battleShipGameB.update(gameRound.battleShipGameB.gameState.length)
    appendLog("Loaded the game")
  }


}


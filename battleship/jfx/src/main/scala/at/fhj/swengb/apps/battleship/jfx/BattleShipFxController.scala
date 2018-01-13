package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import java.nio.file.{Files, Paths}


import at.fhj.swengb.apps.battleship.model._
import at.fhj.swengb.apps.battleship.BattleShipProtobuf
import at.fhj.swengb.apps.battleship.BattleShipProtocol._



class BattleShipFxController extends Initializable {

  @FXML private var welcomeGrid: GridPane = _


  @FXML private var battleGroundGridPane: GridPane = _

  var battleshipGame: BattleShipGame = _

  /**
    * A text area box to place the history of the game
    */
  @FXML private var log: TextArea = _

  @FXML
  def newGame(): Unit = initGame()

  def saveGame(): Unit = saveGameState()

  def loadGame(): Unit = loadGameState()

  override def initialize(url: URL, rb: ResourceBundle): Unit = initGame()

  private def getCellHeight(y: Int): Double = battleGroundGridPane.getRowConstraints.get(y).getPrefHeight

  private def getCellWidth(x: Int): Double = battleGroundGridPane.getColumnConstraints.get(x).getPrefWidth

  def appendLog(message: String): Unit = log.appendText(message + "\n")

  /**
    * Create a new game.
    *
    * This means
    *
    * - resetting all cells to 'empty' state
    * - placing your ships at random on the battleground
    *
    */
  def init(game: BattleShipGame): Unit = {
    battleshipGame = game
    battleGroundGridPane.getChildren.clear()
    for (c <- game.getCells) {
      battleGroundGridPane.add(c, c.pos.x, c.pos.y)
    }
    game.getCells().foreach(c => c.init)
  }


  private def initGame(): Unit = {
    val game: BattleShipGame = createGame()
    init(game)
    appendLog("New game started.")
  }

  private def createGame(): BattleShipGame = {
    val field = BattleField(10, 10, Fleet(FleetConfig.Standard))

    val battleField: BattleField = BattleField.placeRandomly(field)

    BattleShipGame(battleField, getCellWidth, getCellHeight, appendLog)
  }


  def saveGameState(): Unit = {

    convert(battleshipGame).writeTo(Files.newOutputStream(Paths.get("battleship/BattleShipProtobuf.bin")))

    appendLog("Saved the game")

  }

  def loadGameState(): Unit = {

    val reload = BattleShipProtobuf.BattleShipGame.parseFrom(Files.newInputStream(Paths.get("battleship/BattleShipProtobuf.bin")))

    val gameWithOldValues = BattleShipGame(convert(reload).battleField, getCellWidth, getCellHeight, appendLog)
    gameWithOldValues.gameState = convert(reload).gameState
    init(gameWithOldValues)
    gameWithOldValues.update(battleshipGame.gameState.length)
    appendLog("Loaded the game")


  }
}








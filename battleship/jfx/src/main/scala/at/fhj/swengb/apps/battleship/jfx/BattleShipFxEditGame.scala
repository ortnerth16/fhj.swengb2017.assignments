package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{FXML, Initializable}
import javafx.scene.Scene
import javafx.scene.control.{Label, TextArea}
import javafx.scene.text.Text


import at.fhj.swengb.apps.battleship.model.GameRound
import at.fhj.swengb.apps.battleship.jfx.BattleShipFxNewGame

class BattleShipFxEditGame extends Initializable {


  @FXML private var playerInfo: Text = _


  var game: GameRound = _
  var filename: String = _


  @FXML
  def back(): Unit = backToHome()

  def startGame(): Unit = createGame()

  private def initGame(): Unit = {
    if (BattleShipFxApp.getGameRound() != null) {
      println("DEBUG: now we can load/edit it because we have a game")
      game = BattleShipFxApp.getGameRound()
      filename = BattleShipFxApp.getFilename()
      loadGameRoundForPlayer()

    }
  }

  override def initialize(location: URL, resources: ResourceBundle): Unit = initGame


  def loadGameRoundForPlayer(): Unit = {
    println(game.getNumberCurrentPlayers())
    if (game.getNumberCurrentPlayers() != null) {
      if (game.getNumberCurrentPlayers() == 1) {
        // display edit mode for player
        playerInfo.setText(game.playerA + " @" + game.gameName + " vs " + game.playerB)

      } else if (game.getNumberCurrentPlayers() == 2) {
        // display for 2nd player
        playerInfo.setText(game.playerB + " @" + game.gameName + " vs " + game.playerA)
      }
    }

  }

  def createGame(): Unit = {
    BattleShipFxApp.display(BattleShipFxApp.loadGame, BattleShipFxApp.loadMain)
  }

  def backToHome(): Unit = {
    BattleShipFxApp.display(BattleShipFxApp.loadNewGame, BattleShipFxApp.loadMain)
  }


}
package at.fhj.swengb.apps.battleship.jfx

import java.net.URL
import java.util.{Calendar, ResourceBundle}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.TextArea
import javafx.scene.control.Label

import at.fhj.swengb.apps.battleship.model._

import scala.util.Random

class NewGameController extends Initializable {

  // game name lists
  val list1: List[String] = List("item11", "item12", "item13")

  val list2: List[String] = List("item21", "item32", "item33")

  val list3: List[String] = List("item31", "item32", "item33")


  // game round is created at "create game"
  var game: GameRound = _

  @FXML private var gameName: Label = _
  @FXML private var playerA: TextArea = _
  @FXML private var playerB: TextArea = _

  @FXML
  def back(): Unit = backToHome()
  def next(): Unit = createGame()
  def refresh(): Unit = refreshGameName()

  override def initialize(url: URL, rb: ResourceBundle): Unit = initGame()

  /**
    * TODO:  that we can go back home
    */
  def backToHome(): Unit = {

    // Nothing should be created, just back @home

  }

  /**
    *
    */
  private def initGame(): Unit = {
    refreshGameName()
  }

  /**
    *
    */
  private def createGame(): Unit  = {

    // game infos
    val name = gameName.getText
    val player1 = playerA.getText
    val player2 = playerB.getText

    //battlefield

    // TODO: Eingabevalidierung?

    // create a new game and switch screen

  }

  /**
    *
    * @return a random game name with items form list1, list2, list3
    */
  def getRandomGameName(): String = {
    var name = ""
    name = list1(Random.nextInt(list1.size)) + " " + list2(Random.nextInt(list2.size)) + " " + list3(Random.nextInt(list3.size))
    return name
  }

  /**
    *
    */
  def refreshGameName(): Unit = {
    var name =""
    name = getRandomGameName()
    gameName.setText(name)
  }

  /**
    *
    * @return the gameRound which was created the screen before
    */
  def getGameRound(): GameRound = {
    return game
  }

}

package at.fhj.swengb.apps.battleship.jfx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import scala.util.{Failure, Success, Try}



object BattleShipFxApp {



  def main(args: Array[String]): Unit = {

    Application.launch(classOf[BattleShipFxApp], args: _*)

  }
}




class BattleShipFxApp extends Application {

  val triedRoot = Try(FXMLLoader.load[Parent](getClass.getResource("/at/fhj/swengb/apps/battleship/jfx/battleshipfx.fxml")))
  val welcomeScreen = Try(FXMLLoader.load[Parent](getClass.getResource("/at/fhj/swengb/apps/battleship/jfx/welcome_screen.fxml")))

  val css = "/at/fhj/swengb/apps/battleship/jfx/battleship.css"

  override def start(stage: Stage): Unit = {
    welcomeScreen match {
      case Success(root) =>
        stage.setScene(new Scene(root))
        stage.show()
        stage.getScene.getStylesheets.clear()
        stage.getScene.getStylesheets.add(css)
        stage.setTitle("BattleShip")
      case Failure(e) => e.printStackTrace()
    }
  }

}
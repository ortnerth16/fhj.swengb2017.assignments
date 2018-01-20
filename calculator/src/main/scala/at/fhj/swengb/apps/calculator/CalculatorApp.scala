package at.fhj.swengb.apps.calculator


import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.beans.property.{ObjectProperty, SimpleObjectProperty}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.TextField
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.util.{Failure, Success}
import scala.util.control.NonFatal

object CalculatorApp {

  def main(args: Array[String]): Unit = {
    Application.launch(classOf[CalculatorFX], args: _*)
  }
}

class CalculatorFX extends javafx.application.Application {

  val fxml = "/at/fhj/swengb/apps/calculator/calculator.fxml"
  val css = "/at/fhj/swengb/apps/calculator/calculator.css"

  def mkFxmlLoader(fxml: String): FXMLLoader = {
    new FXMLLoader(getClass.getResource(fxml))
  }

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("Calculator")
      setSkin(stage, fxml, css)
      stage.show()
      stage.setMinWidth(stage.getWidth)
      stage.setMinHeight(stage.getHeight)
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

  def setSkin(stage: Stage, fxml: String, css: String): Boolean = {
    val scene = new Scene(mkFxmlLoader(fxml).load[Parent]())
    stage.setScene(scene)
    stage.getScene.getStylesheets.clear()
    stage.getScene.getStylesheets.add(css)
  }

}

class CalculatorFxController extends Initializable {

  val calculatorProperty: ObjectProperty[RpnCalculator] = new SimpleObjectProperty[RpnCalculator](RpnCalculator())

  def getCalculator(): RpnCalculator = calculatorProperty.get()

  def setCalculator(rpnCalculator: RpnCalculator): Unit = calculatorProperty.set(rpnCalculator)

  @FXML var output: TextField = _

  override def initialize(location: URL, resources: ResourceBundle) = {

  }

  //numbers
  def numberNine(): Unit = {
    output.appendText("9")
  }

  def numberEight(): Unit = {
    output.appendText("8")
  }

  def numberSeven(): Unit = {
    println("an event has happened")
  }

  def numberSix(): Unit = {
    println("an event has happened")
  }

  def numberFive(): Unit = {
    println("an event has happened")
  }

  def numberFour(): Unit = {
    println("an event has happened")
  }

  def numberThree(): Unit = {
    println("an event has happened")
  }

  def numberTwo(): Unit = {
    println("an event has happened")
  }

  def numberOne(): Unit = {
    println("an event has happened")
  }

  def numberZero(): Unit = {
    println("an event has happened")
  }


  // Operators
  def plus(): Unit = {
    println("an event has happened")
  }

  def minus(): Unit = {
    println("an event has happened")
  }

  def divide(): Unit = {
    println("an event has happened")
  }

  def multiply(): Unit = {
    println("an event has happened")
  }

  def enter(): Unit = {
    println("an event has happened")
  }

  // Special signs

  def drop(): Unit = {
    println("an event has happened")
  }

  def numberUp(): Unit = {
    println("an event has happened")
  }

  def numberDown(): Unit = {
    println("an event has happened")
  }

  def smth(): Unit = {
    println("an event has happened")
  }


  def clear(): Unit = {
    output.setText("")
  }

  def percent(): Unit = {
    println("an event has happened")
  }

  def changeSign(): Unit = {
    println("an event has happened")
  }

  def comma(): Unit = {
    println("an event has happened")
  }
}


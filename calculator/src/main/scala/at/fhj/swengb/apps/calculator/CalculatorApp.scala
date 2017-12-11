package at.fhj.swengb.apps.calculator

import java.awt.event.ActionEvent
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

  def getCalculator() : RpnCalculator = calculatorProperty.get()

  def setCalculator(rpnCalculator : RpnCalculator) : Unit = calculatorProperty.set(rpnCalculator)

  @FXML var numberTextField1 : TextField = _
  @FXML var numberTextField2 : TextField = _
  @FXML var numberTextField3 : TextField = _

  override def initialize(location: URL, resources: ResourceBundle) = {

  }

  def tell(text : String): Unit = {
    getCalculator().stack.size match {
        case 0 => numberTextField1.setText(numberTextField1.getText()+text)
        case 1 => numberTextField2.setText(numberTextField2.getText()+text)
        case 2 => numberTextField3.setText(numberTextField3.getText()+text)
        case _ =>
    }
  }

  def numZero(): Unit = {
    tell("0")
  }
  def numOne(): Unit = {
    tell("1")
  }
  def numTwo(): Unit = {
    tell("2")
  }
  def numThree(): Unit = {
    tell("3")
  }
  def numFour(): Unit = {
    tell("4")
  }
  def numFive(): Unit = {
    tell("5")
  }
  def numSix(): Unit = {
    tell("6")
  }
  def numSeven(): Unit = {
    tell("7")
  }
  def numEight(): Unit = {
    tell("8")
  }
  def numNine(): Unit = {
    tell("9")
  }


  def sgn(): Unit = {
    getCalculator().push(Op(numberTextField1.getText)) match {
      case Success(c) => setCalculator(c)
      case Failure(e) =>
    }
    getCalculator().stack foreach println
  }


  def add(): Unit = {
    getCalculator().push(Add) match {
      case Success(c) => setCalculator(c)
      case Failure(e) =>
    }
    getCalculator().stack foreach println
    update()
  }

  def subtract(): Unit = {
    getCalculator().push(Sub) match {
      case Success(c) => setCalculator(c)
      case Failure(e) =>
    }
    getCalculator().stack foreach println
    update()
  }

  def multiply(): Unit = {
    getCalculator().push(Mul) match {
      case Success(c) => setCalculator(c)
      case Failure(e) =>
    }
    getCalculator().stack foreach println
    update()
  }

  def divide(): Unit = {
    val div = getCalculator().stack.size match {
      case 2 => {
        val a = numberTextField2.getText()
        numberTextField2.setText("No Number")
        numberTextField3.setDisable(true)
        numberTextField2.setDisable(false)
        a
      }
      case 3 => {
        val a = numberTextField3.getText()
        numberTextField3.setText("No Number")
        numberTextField3.setDisable(false)
        a
      }
    }
    if (div != "0.0") {
      getCalculator().push(Div) match {
        case Success(c) => setCalculator(c)
        case Failure(e) => // show warning / error
      }
      update()
    }
    else {
      setCalculator(getCalculator().pop()._2)
    }
    getCalculator().stack foreach println

  }

  def update(): Unit = {
    val calcu = getCalculator()
    if(calcu.stack.size>0) {
        numberTextField1.setText(calcu.stack(0) match {
        case Val(x) => x.toString
         })
      if (calcu.stack.size > 1) {
          calcu.stack foreach println
          numberTextField2.setText(calcu.stack(1) match {
          case Val(x) => x.toString
          })
        if (calcu.stack.size > 2) {
            numberTextField3.setText(calcu.stack(2) match {
            case Val(x) => x.toString
            })
        }
        else numberTextField3.setText("")
      }
      else {
          numberTextField2.setText("")
          numberTextField3.setText("")
      }
    }
    else {
        numberTextField1.setText("")
        numberTextField2.setText("")
        numberTextField3.setText("")
    }
    calcu.stack.size match {
        case 0 => {
          numberTextField1.setDisable(false)
          numberTextField2.setDisable(true)
          numberTextField3.setDisable(true)
          }
          
          case 1 => {
            numberTextField1.setDisable(true)
            numberTextField2.setDisable(false)
            numberTextField3.setDisable(true)
          }
          
          case 2 => {
            numberTextField1.setDisable(true)
            numberTextField2.setDisable(true)
            numberTextField3.setDisable(false)
          }
          
          case 3 => {
            numberTextField1.setDisable(true)
            numberTextField2.setDisable(true)
            numberTextField3.setDisable(true)
          }
    }
  }
  
  def enter(): Unit = {
    val num : String = {
      getCalculator().stack.size match {
        case 0 => {
          numberTextField1.getText()
        }
        case 1 => {
          numberTextField2.getText()
        }
        case 2 => {
          numberTextField3.getText()
        }
        case _ => "ung"
      }
    }
    if (num != "ung") {
      getCalculator().push(Op(num)) match {
        case Success(c) => setCalculator(c)
        case Failure(e) => // show warning / error
      }
      update()
    }
  }



  def clear(): Unit = {
    getCalculator().stack.size match {
      case 0 => {
        if(numberTextField1.getText != "0")
          numberTextField1.setText("0")
        else {
          setCalculator(RpnCalculator())
          update()
        }
      }
      case 1 => {
        if(numberTextField2.getText != "0")
          numberTextField2.setText("0")
        else {
          setCalculator(RpnCalculator())
          update()
        }
      }
      case 2 => {
        if(numberTextField3.getText != "0")
          numberTextField3.setText("0")
        else {
          setCalculator(RpnCalculator())
          update()
        }
      }
      case 3 => {
        setCalculator(RpnCalculator())
        update()
      }
    }
  }

  def comma(): Unit = {
    getCalculator().stack.size match {
      case 0 => {
        if (!numberTextField1.getText().contains("."))
          numberTextField1.setText(numberTextField1.getText()+".")
      }
      case 1 => {
        if (!numberTextField2.getText().contains("."))
          numberTextField2.setText(numberTextField2.getText()+".")
      }
      case 2 => {
        if (!numberTextField3.getText().contains("."))
          numberTextField3.setText(numberTextField3.getText()+".")
      }
    }
  }

}
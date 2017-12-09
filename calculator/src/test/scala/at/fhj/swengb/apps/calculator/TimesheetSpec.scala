package at.fhj.swengb.apps.calculator

import java.nio.file.{Files, Path, Paths}
import java.util
import scala.collection.JavaConverters._

import org.scalatest.WordSpecLike

//import scala.reflect.io.Path


class TimesheetSpec extends WordSpecLike {

  val expected =
    """|== Time expenditure: Calculator assignment
       |
       |[cols="1,1,4", options="header"]
       |.Time expenditure
       ||===
       || Date
       || Hours
       || Description
       |
       || 2.12.17
       || 2
       || Created the GUI
       |
       || 5.12.17
       || 2
       || Finished the GUI and implemented some button functionality
       |
       |
       ||===
       |""".stripMargin



  "content is equal" in {
    val path: Path = Paths.get("D:\\Dokumente\\FH\\SWENGB\\workspace\\fhj.swengb2017.assignments\\calculator\\timesheet-calculator.adoc")
    val content: util.List[String] = Files.readAllLines(path)
    var lines: String = ""
    val result = content.asScala.mkString("\n")

    for (l <- content.asScala) {
      //println(l)
      lines += l
      lines += "\n"
    }
    println(lines)
    assert(expected.equals(result))
  }


}

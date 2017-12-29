package at.fhj.swengb.apps.battleship

import scala.collection.JavaConverters._
import at.fhj.swengb.apps.battleship.BattleShipProtobuf.Direction
import at.fhj.swengb.apps.battleship.model._

object BattleShipProtocol {

  // Convert to BattleShipProtobuf
  def convert(g: BattleShipGame): BattleShipProtobuf.BattleShipGame = {
    BattleShipProtobuf.BattleShipGame.newBuilder()
      .setBattlefield(convert(g.battleField))
      .setCellWidth(g.battleField.width)
      .setCellHeight(g.battleField.height)
      .setLog(g.log.toString())
      .addAllCells(g.gameState.map(convert).asJava).build()

  }

  def convert(g: Vessel): BattleShipProtobuf.Vessel = {
    val direction = {
      g.direction match {
        case Horizontal => Direction.Horizontal
        case Vertical => Direction.Vertical
      }
    }

    BattleShipProtobuf.Vessel.newBuilder()
      .setName(g.name.value)
      .setStartPos(convert(g.startPos))
      .setDirection(direction)
      .setSize(g.size).build()
  }

  def convert(g: Fleet): BattleShipProtobuf.Fleet = {
    BattleShipProtobuf.Fleet.newBuilder().addAllVessel(g.vessels.map(convert).asJava).build()
  }

  def convert(g: BattleField): BattleShipProtobuf.BattleField = {
    BattleShipProtobuf.BattleField.newBuilder()
      .setFleet(convert(g.fleet))
      .setWith(g.width)
      .setHeight(g.height).build()
  }

  def convert(g: BattlePos): BattleShipProtobuf.BattlePos = BattleShipProtobuf.BattlePos.newBuilder().setX(g.x).setY(g.y).build()

  // Convert back
  def convert(g: BattleShipProtobuf.Vessel): Vessel = {
    val direction = {
      g.getDirection match {
        case Direction.Horizontal => Horizontal
        case Direction.Vertical => Vertical
      }
    }
      Vessel(NonEmptyString(g.getName), BattlePos(g.getStartPos.getX, g.getStartPos.getY), direction, g.getSize)

  }

  def convert(g: BattleShipProtobuf.Fleet): Fleet = {
    val vessels: Set[Vessel] = g.getVesselList.asScala.map(x => convert(x)).toSet
    Fleet(vessels)
  }

  def convert(g: BattleShipProtobuf.BattleField) : BattleField = BattleField(g.getWith, g.getHeight, convert(g.getFleet))

  def convert(g: BattleShipProtobuf.BattleShipGame): BattleShipGame = {

    val battleship = BattleShipGame(convert(g.getBattlefield), (x: Int) => x.toDouble, (x: Int) => x.toDouble, x => ())
    g.getCellsList.asScala.map(x => convert(x)).foreach(battleship.clickedCells)
    battleship
  }

  def convert(g: BattleShipProtobuf.BattlePos): BattlePos = BattlePos(g.getX, g.getY)
}


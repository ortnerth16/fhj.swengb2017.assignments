package at.fhj.swengb.apps.battleship

import scala.collection.JavaConverters._
import at.fhj.swengb.apps.battleship.BattleShipProtobuf.{BattlePos, Direction}
import at.fhj.swengb.apps.battleship.model._

object BattleShipProtocol {

  // Convert to BattleShipProtobuf

  def convert(g : BattleShipGame) : BattleShipProtobuf.BattleShipGame = {
    BattleShipProtobuf.BattleShipGame.newBuilder()
        .setBattlefield(convert(g.battleField))
        .setCellWidth(g.battleField.width)
        .setCellHeight(g.battleField.height)
        .setLog(g.log.toString()).build()

  }

  def convert(g: Vessel) : BattleShipProtobuf.Vessel = {
    val direction = {
      g.direction match {
        case Horizontal => Direction.Horizontal
        case Vertical => Direction.Vertical
      }
    }

    BattleShipProtobuf.Vessel.newBuilder()
      .setName(g.name.value)
      .setStartPos(BattlePos.newBuilder().setX(g.startPos.x).setY(g.startPos.y))
      .setDirection(direction)
      .setSize(g.size).build()
  }

  def convert(g: Fleet) : BattleShipProtobuf.Fleet = {
    BattleShipProtobuf.Fleet.newBuilder().addAllVessel(g.vessels.map(convert).asJava).build()
  }

  def convert(g: BattleField) : BattleShipProtobuf.BattleField = {
    BattleShipProtobuf.BattleField.newBuilder()
      .setFleet(convert(g.fleet))
      .setWith(g.width)
      .setHeight(g.height).build()
  }

  def convert(g: BattlePos) : BattleShipProtobuf.BattlePos = BattleShipProtobuf.BattlePos.newBuilder().setX(g.getX).setY(g.getY).build()

  // Convert back

  def convert(g : BattleShipProtobuf.BattleShipGame) : BattleShipGame = ???


}

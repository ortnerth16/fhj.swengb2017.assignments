package at.fhj.swengb.apps.battleship

import at.fhj.swengb.apps.battleship.BattleShipProtobuf.{BattlePos, Direction}
import at.fhj.swengb.apps.battleship.model.{BattleShipGame, Horizontal, Vertical, Vessel, Fleet}

object BattleShipProtocol {

  def convert(g : BattleShipGame) : BattleShipProtobuf.BattleShipGame = ???

  def convert(g : BattleShipProtobuf.BattleShipGame) : BattleShipGame = ???

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

  def convert(g: Fleet) : BattleShipProtobuf.Fleet = ???

  /*def convert(g: BattlePos) : BattleShipProtobuf.BattlePos = {
    BattleShipProtobuf.BattlePos.newBuilder().setX(g.getX).setY(g.getY).build()
  }

  def convert(g: BattleShipProtobuf.BattlePos) : BattlePos = ???*/

}

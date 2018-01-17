package at.fhj.swengb.apps.battleship

import scala.collection.JavaConverters._
import at.fhj.swengb.apps.battleship.BattleShipProtobuf.Game.Direction
import at.fhj.swengb.apps.battleship.model._

object BattleShipProtocol {

  // Convert to BattleShipProtobuf
  def convert(g: GameRound): BattleShipProtobuf.Game = {
    BattleShipProtobuf.Game.newBuilder()
      .setGameA(convert(g.getGameA))
      .setGameB(convert(g.getGameB)).build()
  }

  def convert(g: BattleShipGame): BattleShipProtobuf.Game.BattleShipGame = {
    BattleShipProtobuf.Game.BattleShipGame.newBuilder()
      .setBattlefield(convert(g.battleField))
      .setCellWidth(g.battleField.width)
      .setCellHeight(g.battleField.height)
      .addAllCells(g.gameState.map(convert).asJava).build()

  }

  def convert(g: Vessel): BattleShipProtobuf.Game.Vessel = {
    val direction = {
      g.direction match {
        case Horizontal => Direction.Horizontal
        case Vertical => Direction.Vertical
      }
    }

    BattleShipProtobuf.Game.Vessel.newBuilder()
      .setName(g.name.value)
      .setStartPos(convert(g.startPos))
      .setDirection(direction)
      .setSize(g.size).build()
  }

  def convert(g: Fleet): BattleShipProtobuf.Game.Fleet = {
    BattleShipProtobuf.Game.Fleet.newBuilder().addAllVessel(g.vessels.map(convert).asJava).build()
  }

  def convert(g: BattleField): BattleShipProtobuf.Game.BattleField = {
    BattleShipProtobuf.Game.BattleField.newBuilder()
      .setFleet(convert(g.fleet))
      .setWith(g.width)
      .setHeight(g.height).build()
  }

  def convert(g: BattlePos): BattleShipProtobuf.Game.BattlePos = BattleShipProtobuf.Game.BattlePos.newBuilder().setX(g.x).setY(g.y).build()

  // Convert back
  def convert(g: BattleShipProtobuf.Game.Vessel): Vessel = {
    val direction = {
      g.getDirection match {
        case Direction.Horizontal => Horizontal
        case Direction.Vertical => Vertical
      }
    }
      Vessel(NonEmptyString(g.getName), BattlePos(g.getStartPos.getX, g.getStartPos.getY), direction, g.getSize)

  }

  def convert(g: BattleShipProtobuf.Game.Fleet): Fleet = {
    val vessels: Set[Vessel] = g.getVesselList.asScala.map(x => convert(x)).toSet
    Fleet(vessels)
  }

  def convert(g: BattleShipProtobuf.Game.BattleField) : BattleField = BattleField(g.getWith, g.getHeight, convert(g.getFleet))

  def convert(g: BattleShipProtobuf.Game.BattleShipGame): BattleShipGame = {

    val battleship = BattleShipGame(convert(g.getBattlefield), (x: Int) => x.toDouble, (x: Int) => x.toDouble, x => (), g.getPlayer)
    g.getCellsList.asScala.map(x => convert(x)).foreach(battleship.clickedCells)
    battleship
  }

  def convert(g: BattleShipProtobuf.Game.BattlePos): BattlePos = BattlePos(g.getX, g.getY)
}


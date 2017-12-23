package at.fhj.swengb.apps.battleship

import at.fhj.swengb.apps.battleship.BattleShipProtobuf.Direction
import at.fhj.swengb.apps.battleship.model._
import at.fhj.swengb.apps.battleship.BattleShipProtocol
import org.scalacheck.{Gen, Prop}
import org.scalatest.WordSpecLike
import org.scalatest.prop.Checkers


class BattleShipGameProtocolSpec extends WordSpecLike {


  "BattleShipProtocol" should {
    /*"be deserializable" in {
      Checkers.check(Prop.forAll(battleShipGameGen) {
        expected: BattleShipGame => {
          val actual = BattleShipProtocol.convert(BattleShipProtocol.convert(expected))
          actual == expected
        }
      })
    }*/
    ".convert(pos : BattlePos)" in {
      val expectedPos = BattlePos(4711, 815)
      val actualPos: BattleShipProtobuf.BattlePos = BattleShipProtocol.convert(expectedPos)

      assert(actualPos.getX == expectedPos.x)
      assert(actualPos.getY == expectedPos.y)
    }

    ".convert(vessel : Vessel)" in {
      val expectedVessel = Vessel(NonEmptyString("Peter"), BattlePos(0,0), Horizontal, 2)
      val actualVessel : BattleShipProtobuf.Vessel = BattleShipProtocol.convert(expectedVessel)

      val direction = {
        actualVessel.getDirection match {
          case Direction.Horizontal => Horizontal
          case Direction.Vertical => Vertical
        }
      }

      assert(actualVessel.getStartPos.getX == expectedVessel.startPos.x)
      assert(actualVessel.getStartPos.getY == expectedVessel.startPos.y)

      assert(direction == expectedVessel.direction)

      assert(actualVessel.getName == expectedVessel.name.value)

      assert(actualVessel.getSize == expectedVessel.size)
    }
   /* ".convert(fleet : Fleet)" in {
      val expectedFleet = Fleet(vessels = )
    }*/
  }
}

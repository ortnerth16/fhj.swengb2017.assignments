package at.fhj.swengb.apps.battleship.model

class GameRound(playerA: String,
                playerB: String,
                battlefieldA: BattleField,
                battlefieldB: BattleField,
                getCellWidth: Int => Double,
                getCellHeight: Int => Double,
                log: String => Unit) {

  val gameA: BattleShipGame = BattleShipGame(battlefieldA, getCellWidth, getCellHeight, log, playerA)
  val gameB: BattleShipGame = BattleShipGame(battlefieldB, getCellWidth, getCellHeight, log, playerB)
}

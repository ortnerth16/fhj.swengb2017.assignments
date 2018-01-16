package at.fhj.swengb.apps.battleship.model

object GameRound {

  def createGame(player: String,
                 getCellWidth: Int => Double,
                 getCellHeight: Int => Double,
                 log: String => Unit): BattleShipGame = {

    val battlefield = BattleField(10, 10, Fleet(FleetConfig.Standard))
    BattleShipGame(battlefield, getCellWidth, getCellHeight, log, player)
  }

  def apply(playerA: String,
            playerB: String,
            getCellWidth: Int => Double,
            getCellHeight: Int => Double,
            log: String => Unit): GameRound = {

    val battleShipGameA = createGame(playerA, getCellWidth, getCellHeight, log)
    val battleShipGameB = createGame(playerB, getCellWidth, getCellHeight, log)

    GameRound(battleShipGameA, battleShipGameB, "Battle of Bearstards")

  }

}

case class GameRound(gameA: BattleShipGame,
                  gameB: BattleShipGame,
                  gameName: String) {


}

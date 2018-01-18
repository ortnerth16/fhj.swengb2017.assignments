package at.fhj.swengb.apps.battleship.model

/*object GameRound {

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


}*/

case class GameRound(playerA: String,
                     playerB: String,
                     gameName: String,
                     log:String => Unit,
                     battleShipGameA: BattleShipGame,
                     battleShipGameB: BattleShipGame,
                     currentPlayer: String) {

  private var numberCurrentPlayers: Int = _
  /*private val = battleShipGameA = createGame(playerA, getCellWidth, getCellHeight, log)
  private val = battleShipGameB = createGame(playerB, getCellWidth, getCellHeight, log)*/

  private def createGame(player: String,
                 getCellWidth: Int => Double,
                 getCellHeight: Int => Double,
                 log: String => Unit): BattleShipGame = {


    val field = BattleField(10, 10, Fleet(FleetConfig.Standard))

    val battlefield: BattleField = BattleField.placeRandomly(field)
    BattleShipGame(battlefield, getCellWidth, getCellHeight, log, player)
  }

  def setNumberCurrentPlayers(number: Int) = this.numberCurrentPlayers = number

  def getNumberCurrentPlayers(): Int = numberCurrentPlayers

}

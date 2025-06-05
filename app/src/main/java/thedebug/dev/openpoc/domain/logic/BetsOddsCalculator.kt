package thedebug.dev.openpoc.domain.logic

import thedebug.dev.openpoc.data.model.Bet

class BetsOddsCalculator {
    fun calculateOdds(bets: List<Bet>): List<Bet> {
        val updatedBet = bets.toMutableList()

        for (i in updatedBet.indices) {
            val bet = updatedBet[i]
            if (bet.type != TYPE_TOTAL_SCORE && bet.type != TYPE_NUMBER_OF_FOULS) {
                if (bet.odds > 0 && bet.type != TYPE_FIRST_GOAL_SCORER) bet.odds--
            } else {
                if (bet.odds < 50) {
                    bet.odds++
                    if (bet.type == TYPE_NUMBER_OF_FOULS) {
                        if (bet.sellIn < 11 && bet.odds < 50) bet.odds++
                        if (bet.sellIn < 6 && bet.odds < 50) bet.odds++
                    }
                }
            }
            if (bet.type != TYPE_FIRST_GOAL_SCORER) bet.sellIn--
            if (bet.sellIn < 0) {
                when (bet.type) {
                    TYPE_TOTAL_SCORE -> if (bet.odds < 50) bet.odds++
                    TYPE_NUMBER_OF_FOULS -> bet.odds = 0
                    else -> if (bet.odds > 0 && bet.type != TYPE_FIRST_GOAL_SCORER) bet.odds--
                }
            }
        }
        return updatedBet.sortedBy { it.sellIn }
    }

    companion object {
        private const val TYPE_TOTAL_SCORE = "Total score"
        private const val TYPE_NUMBER_OF_FOULS = "Number of fouls"
        private const val TYPE_FIRST_GOAL_SCORER = "First goal scorer"
    }

}
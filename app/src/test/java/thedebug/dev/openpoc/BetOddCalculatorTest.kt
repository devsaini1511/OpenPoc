package thedebug.dev.openpoc

import org.junit.Assert
import org.junit.Test
import thedebug.dev.openpoc.data.model.Bet
import thedebug.dev.openpoc.domain.logic.BetsOddsCalculator
import kotlin.collections.first

class BetOddCalculatorTest {

    private val calculator = BetsOddsCalculator()

    @Test
    fun winningTeam_decreasesOddsAndSellIn() {
        val bet = Bet("Winning team", 10, 20, "")
        val result = calculator.calculateOdds(listOf(bet))
        val updated = result.first()
        println("Test: Winning team bet -> Odds: ${updated.odds}, SellIn: ${updated.sellIn}")
        Assert.assertEquals(19, updated.odds)
        Assert.assertEquals(9, updated.sellIn)
    }

    @Test
    fun totalScore_increasesOdds() {
        val bet = Bet("Total score", 10, 20, "")
        val result = calculator.calculateOdds(listOf(bet))
        val updated = result.first()
        println("Test: Total score bet -> Odds: ${updated.odds}, SellIn: ${updated.sellIn}")
        Assert.assertEquals(21, updated.odds)
        Assert.assertEquals(9, updated.sellIn)
    }

    @Test
    fun fouls_sellIn11_increasesOddsBy1() {
        val bet = Bet("Number of fouls", 11, 20, "")
        val result = calculator.calculateOdds(listOf(bet))
        val updated = result.first()
        println("Test: Fouls bet (sellIn=11) -> Odds: ${updated.odds}, SellIn: ${updated.sellIn}")
        Assert.assertEquals(21, updated.odds)
        Assert.assertEquals(10, updated.sellIn)
    }

    @Test
    fun fouls_sellIn10_increasesOddsBy2() {
        val bet = Bet("Number of fouls", 10, 20, "")
        val result = calculator.calculateOdds(listOf(bet))
        val updated = result.first()
        println("Test: Fouls bet (sellIn=10) -> Odds: ${updated.odds}, SellIn: ${updated.sellIn}")
        Assert.assertEquals(22, updated.odds)
        Assert.assertEquals(9, updated.sellIn)
    }

    @Test
    fun fouls_sellIn5_increasesOddsBy3() {
        val bet = Bet("Number of fouls", 5, 20, "")
        val result = calculator.calculateOdds(listOf(bet))
        val updated = result.first()
        println("Test: Fouls bet (sellIn=5) -> Odds: ${updated.odds}, SellIn: ${updated.sellIn}")
        Assert.assertEquals(23, updated.odds)
        Assert.assertEquals(4, updated.sellIn)
    }

    @Test
    fun totalScore_atMaxOdds_doesNotExceedLimitBy50() {
        val bet = Bet("Total score", 5, 50, "")
        val result = calculator.calculateOdds(listOf(bet)).first()
        println("totalScore maxOdds -> Odds: ${result.odds}, SellIn: ${result.sellIn}")
        Assert.assertEquals(50, result.odds)
        Assert.assertEquals(4, result.sellIn)
    }
}
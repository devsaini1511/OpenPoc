package thedebug.dev.openpoc.data.repository

import thedebug.dev.openpoc.data.model.Bet
import thedebug.dev.openpoc.domain.logic.BetsOddsCalculator
import thedebug.dev.openpoc.domain.repository.BetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BetRepositoryImpl @Inject constructor(
    private val oddsCalculator: BetsOddsCalculator,
) : BetRepository {

    //Static list of bet
    private val _bets = MutableStateFlow(
        listOf(
            Bet("Winning team", 10, 20, "https://i.imgur.com/mx66SBD.jpeg"),
            Bet("Total score", 2, 0, "https://i.imgur.com/VnPRqcv.jpeg"),
            Bet("Player performance", 5, 7, "https://i.imgur.com/Urpc00H.jpeg"),
            Bet("First goal scorer", 0, 80, "https://i.imgur.com/Wy94Tt7.jpeg"),
            Bet("Number of fouls", 5, 49, "https://i.imgur.com/NMLpcKj.jpeg"),
            Bet("Corner kicks", 3, 6, "https://i.imgur.com/TiJ8y5l.jpeg")
        )
    )

    override fun getBets(): Flow<List<Bet>> = _bets
        .map { it.sortedBy { it.sellIn } }
        .flowOn(Dispatchers.Default)

    override suspend fun calculateOdds(updated: List<Bet>) {
        _bets.value = updated
    }
}

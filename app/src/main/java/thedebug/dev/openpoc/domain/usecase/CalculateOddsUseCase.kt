package thedebug.dev.openpoc.domain.usecase

import thedebug.dev.openpoc.data.model.Bet
import thedebug.dev.openpoc.domain.logic.BetsOddsCalculator
import thedebug.dev.openpoc.domain.repository.BetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculateOddsUseCase @Inject constructor(
    private val repository: BetRepository,
    val betsOddsCalculator: BetsOddsCalculator,
) {
    suspend operator fun invoke(): List<Bet> = withContext(Dispatchers.Default) {
        val currentBets = repository.getBets().first()
        val updated = betsOddsCalculator.calculateOdds(currentBets)
        repository.calculateOdds(updated)
        updated
    }
}
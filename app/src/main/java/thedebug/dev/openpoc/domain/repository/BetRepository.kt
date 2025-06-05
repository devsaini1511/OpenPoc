package thedebug.dev.openpoc.domain.repository

import thedebug.dev.openpoc.data.model.Bet
import kotlinx.coroutines.flow.Flow

interface BetRepository {
     fun getBets(): Flow<List<Bet>>
    suspend fun calculateOdds(updated: List<Bet>)
}
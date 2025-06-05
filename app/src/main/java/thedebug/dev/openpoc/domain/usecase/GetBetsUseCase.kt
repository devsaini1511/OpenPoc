package thedebug.dev.openpoc.domain.usecase

import thedebug.dev.openpoc.data.model.Bet
import thedebug.dev.openpoc.domain.repository.BetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBetsUseCase @Inject constructor(private val repository: BetRepository) {
    operator fun invoke(): Flow<List<Bet>> {
        return repository.getBets()
    }
}

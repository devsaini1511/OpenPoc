package thedebug.dev.openpoc.di

import thedebug.dev.openpoc.data.repository.BetRepositoryImpl
import thedebug.dev.openpoc.domain.logic.BetsOddsCalculator
import thedebug.dev.openpoc.domain.repository.BetRepository
import thedebug.dev.openpoc.domain.usecase.CalculateOddsUseCase
import thedebug.dev.openpoc.domain.usecase.GetBetsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBetOddsCalculator(): BetsOddsCalculator = BetsOddsCalculator()

    @Provides
    @Singleton
    fun provideBetRepository(
        oddsCalculator: BetsOddsCalculator
    ): BetRepository = BetRepositoryImpl(oddsCalculator)

    @Provides
    fun provideGetBetsUseCase(repository: BetRepository) = GetBetsUseCase(repository)

    @Provides
    fun provideCalculateOddsUseCase(repository: BetRepository,oddsCalculator: BetsOddsCalculator) = CalculateOddsUseCase(repository,oddsCalculator)

}
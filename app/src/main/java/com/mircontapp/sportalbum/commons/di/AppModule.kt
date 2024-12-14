package com.mircontapp.sportalbum.commons.di

import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.datasource.BupiDataSourceImpl
import com.mircontapp.sportalbum.domain.datasource.AlbumDataSource
import com.mircontapp.sportalbum.data.datasource.DatabaseDataSource
import com.mircontapp.sportalbum.data.datasource.FirebaseDataSource
import com.mircontapp.sportalbum.data.repository.BupiPlayersRepositoryImpl
import com.mircontapp.sportalbum.data.repository.BupiTeamsRepositoryImpl
import com.mircontapp.sportalbum.data.repository.MediaRepositoryImpl
import com.mircontapp.sportalbum.data.repository.PlayersRepositoryImpl
import com.mircontapp.sportalbum.data.repository.TeamsRepositoryImpl
import com.mircontapp.sportalbum.domain.datasource.BupiDataSource
import com.mircontapp.sportalbum.domain.datasource.MediaDataSource
import com.mircontapp.sportalbum.domain.repository.BupiPlayersRepository
import com.mircontapp.sportalbum.domain.repository.BupiTeamsRepository
import com.mircontapp.sportalbum.domain.repository.MediaRepository
import com.mircontapp.sportalbum.domain.repository.PlayersRepository
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import com.mircontapp.sportalbum.domain.usecases.DoDrawUC
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsSuperlegaUC
import com.mircontapp.sportalbum.domain.usecases.GetVideosByNameUC
import com.mircontapp.sportalbum.domain.usecases.InsertTeamUC
import com.mircontapp.sportalbum.domain.usecases.UpdateTeamUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAlbumDataSource(): AlbumDataSource {
        //return AssetsDataSource(SportAlbumApplication.instance.applicationContext.assets)
        return DatabaseDataSource()
    }

    @Singleton
    @Provides
    fun provideMediaDataSource(): MediaDataSource{
        return FirebaseDataSource(SportAlbumApplication.instance.applicationContext)
    }

    @Singleton
    @Provides
    fun provideBupiDataSource(): BupiDataSource {
        return BupiDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideTeamsRepository(): TeamsRepository {
        return TeamsRepositoryImpl(provideAlbumDataSource())
    }

    @Singleton
    @Provides
    fun providePlayersRepository(): PlayersRepository {
        return PlayersRepositoryImpl(provideAlbumDataSource())
    }

    @Singleton
    @Provides
    fun provideMediaRepository() : MediaRepository {
        return MediaRepositoryImpl(provideMediaDataSource())
    }

    @Singleton
    @Provides
    fun provideBupiTeamsRepository(): BupiTeamsRepository {
        return BupiTeamsRepositoryImpl(provideBupiDataSource())
    }

    @Singleton
    @Provides
    fun provideBupiPlayersRepository(): BupiPlayersRepository {
        return BupiPlayersRepositoryImpl(provideBupiDataSource())
    }

    @Singleton
    @Provides
    fun provideGetAllTeamsUC(): GetAllTeamsUC {
        return GetAllTeamsUC(provideTeamsRepository())
    }

    @Singleton
    @Provides
    fun provideGetAllPlayersUC(): GetAllPlayersUC {
        return GetAllPlayersUC(providePlayersRepository())
    }

    @Singleton
    @Provides
    fun provideGetTeamsFromArea(): GetTeamsFromAreaUC {
        return GetTeamsFromAreaUC(provideTeamsRepository())
    }

    @Singleton
    @Provides
    fun provideUpdateTeam(): UpdateTeamUC {
        return UpdateTeamUC(provideTeamsRepository())
    }

    @Singleton
    @Provides
    fun provideInsertTeam(): InsertTeamUC {
        return InsertTeamUC(provideTeamsRepository())
    }

    @Singleton
    @Provides
    fun provideGetTeamsSuperlegaUC(): GetTeamsSuperlegaUC {
        return GetTeamsSuperlegaUC(provideTeamsRepository())
    }

    @Singleton
    @Provides
    fun provideGetVideosByNameUC(): GetVideosByNameUC {
        return GetVideosByNameUC(provideMediaRepository())
    }

}
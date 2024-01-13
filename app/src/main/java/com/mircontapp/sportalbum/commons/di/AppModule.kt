package com.mircontapp.sportalbum.commons.di

import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.datasource.AlbumDataSource
import com.mircontapp.sportalbum.data.datasource.AssetsDataSource
import com.mircontapp.sportalbum.data.repository.TeamsRepositoryImpl
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideAlbumDataSource(): AlbumDataSource {
        return AssetsDataSource(SportAlbumApplication.instance.applicationContext.assets)
    }

    @Provides
    fun provideTeamsRepository(): TeamsRepository {
        return TeamsRepositoryImpl(provideAlbumDataSource())
    }

    @Provides
    fun provideGetAllTeamsUC(): GetAllTeamsUC {
        return GetAllTeamsUC(provideTeamsRepository())
    }

    @Provides
    fun provideGetTeamsFromArea(): GetTeamsFromAreaUC {
        return GetTeamsFromAreaUC(provideTeamsRepository())
    }




}
package com.renad.demoforlist.data.di

import com.renad.demoforlist.data.RecipesRepositoryImp
import com.renad.demoforlist.data.source.RecipesDataSource
import com.renad.demoforlist.data.source.remote.RecipesRemoteDataSourceImp
import com.renad.demoforlist.domain.repos.RecipesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {


    @Provides
    @Singleton
    fun provideRecipesDataSource(recipesRemoteDataSourceImp: RecipesRemoteDataSourceImp): RecipesDataSource =
        recipesRemoteDataSourceImp

   @Provides
    @Singleton
    fun provideRecipesRepository(recipesRepositoryImp: RecipesRepositoryImp): RecipesRepository =
       recipesRepositoryImp


}
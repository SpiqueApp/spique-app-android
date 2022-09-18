package com.aaron.spique.ui.phraselist.module

import com.aaron.spique.ui.phraselist.ui.viewmodel.ColorMapper
import com.aaron.spique.ui.phraselist.ui.viewmodel.ColorMapperColorResources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class PhraseGridModule {

    @Binds
    abstract fun bindColorMapper(impl: ColorMapperColorResources): ColorMapper
}
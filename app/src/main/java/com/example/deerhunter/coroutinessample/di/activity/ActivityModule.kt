package com.example.deerhunter.coroutinessample.di.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.example.deerhunter.coroutinessample.ui.utilities.UiCalculator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    @ActivityScope
    internal fun provideUiCalculator(context: Context): UiCalculator {
        return UiCalculator(context)
    }

    @Provides
    @ActivityScope
    internal fun provideRowLayoutData(uiCalculator: UiCalculator): UiCalculator.RowLayoutData {
        return uiCalculator.calculateRowData()
    }
}

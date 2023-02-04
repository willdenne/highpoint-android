package will.denne.launches.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesDataRepository(
        apiService: ApiService
    ): DataRepository {
        return DataRepositoryImpl(
            apiService = apiService
        )
    }

    @Singleton
    @Provides
    fun provideMoshiAdapter(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

}

package will.denne.launches.data

import retrofit2.Response
import will.denne.launches.data.dataclass.HighPoint
import will.denne.launches.data.dataclass.HighPointRequest
import javax.inject.Inject

interface DataRepository {
    suspend fun getPeaks(): Response<List<HighPoint>>
    suspend fun getHighPoint(state: String): Response<HighPoint>
}

class DataRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): DataRepository {
    override suspend fun getPeaks(): Response<List<HighPoint>> {
        return apiService.getContent()
    }

    override suspend fun getHighPoint(
         state: String
    ): Response<HighPoint> {
        return apiService.getHighPoint(
            HighPointRequest(state)
        )
    }
}
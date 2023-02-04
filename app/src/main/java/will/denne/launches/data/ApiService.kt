package will.denne.launches.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import will.denne.launches.data.dataclass.HighPoint
import will.denne.launches.data.dataclass.HighPointRequest

interface ApiService {

    @GET("/states")
    suspend fun getContent(
    ) : Response<List<HighPoint>>

    @POST("/content")
    suspend fun getHighPoint(
        @Body highPointRequest: HighPointRequest
    ) : Response<HighPoint>
}

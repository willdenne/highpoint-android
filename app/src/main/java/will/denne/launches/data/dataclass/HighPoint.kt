package will.denne.launches.data.dataclass

import com.squareup.moshi.Json

data class HighPoint(
    @Json(name = "url-key") val urlKey: String,
    @Json(name = "Elevation") val elevation: Int,
    @Json(name = "Name") val name: String,
    @Json(name = "State") val state: String,
    @Json(name = "desc-1") val desc1: String?,
    @Json(name = "desc-2") val desc2: String?,
    @Json(name = "image-1") val image1: String?,
    @Json(name = "image-2") val image2: String?,
    @Json(name = "image-self") val imageSelf: String?,
    @Json(name = "date-stamp") val dateStamp: String
)

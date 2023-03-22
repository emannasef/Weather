package eg.gov.iti.jets.mad.weather.model

data class MyResponse(

    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: CurrentWeather
)




data class CurrentWeather(
    val sunrise: String? = null,
    var temp: Double? = null,
    var visibility: Int? = null,
    val uvi: String? = null,
    val clouds: String? = null,
    var feelsLike: Int? = null,
    val dt: String? = null,
    val windDeg: Int? = null,
    var dewPoint: Int? = null,
    val sunset: String? = null,
    val weather: Weather? = null,
    val humidity: String? = null,
    var windSpeed: Int? = null
)







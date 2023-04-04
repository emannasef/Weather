package eg.gov.iti.jets.mad.weather.model

data class MyResponse(
    val alerts: List<Alert>?=null,
    val current: Current?=null,
    val daily: List<Daily>?=emptyList(),
    val hourly: List<Hourly>?=emptyList(),
    val lat: Double?=null,
    val lon: Double?=null,
    val timezone: String?=null,
    val timezone_offset: Int?=null
) {
    data class Alert(
        val description: String,
        val end: Int,
        val event: String,
        val sender_name: String,
        val start: Int,
        val tags: List<String>
    )

    data class Current(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: Double,
        val humidity: Int,
        val pressure: Int,
        val sunrise: Int,
        val sunset: Int,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_speed: Double
    ) {
        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }

    data class Daily(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: FeelsLike,
        val humidity: Int,
        val moon_phase: Double,
        val moonrise: Int,
        val moonset: Int,
        val pop: Double,
        val pressure: Int,
        val rain: Double,
        val snow: Double,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val uvi: Double,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    ) {
        data class FeelsLike(
            val day: Double,
            val eve: Double,
            val morn: Double,
            val night: Double
        )

        data class Temp(
            val day: Double,
            val eve: Double,
            val max: Double,
            val min: Double,
            val morn: Double,
            val night: Double
        )

        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }

    data class Hourly(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: Double,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val rain: Rain,
        val snow: Snow,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    ) {
        data class Rain(
            val `1h`: Double
        )

        data class Snow(
            val `1h`: Double
        )

        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }
}
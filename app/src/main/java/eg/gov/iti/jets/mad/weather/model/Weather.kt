package eg.gov.iti.jets.mad.weather.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String  //clear //rain //clouds
)
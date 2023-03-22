package eg.gov.iti.jets.mad.weather.utlits



import eg.gov.iti.jets.mad.weather.model.MyResponse


sealed class ApiState {
    class Success(val data: MyResponse ):ApiState()
    class Failure(val msg:Throwable):ApiState()
    object Loading:ApiState()
}

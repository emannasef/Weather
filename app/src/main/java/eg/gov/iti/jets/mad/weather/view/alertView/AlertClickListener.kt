package eg.gov.iti.jets.mad.weather.view.alertView

import eg.gov.iti.jets.mad.weather.model.MyAlert

interface AlertClickListener {
    fun deleteAlert(myAlert: MyAlert)
}
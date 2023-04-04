package eg.gov.iti.jets.mad.weather.viewModel.alert

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.gov.iti.jets.mad.weather.MainRule
import eg.gov.iti.jets.mad.weather.database.FakeLocalSource
import eg.gov.iti.jets.mad.weather.model.FakeRepository
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.network.FakeWeatherClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.Assert.*

import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class AlertsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainRule = MainRule()

    private lateinit var repo: FakeRepository
    lateinit var alertsViewModel: AlertsViewModel
    private var myResponse = MyResponse()

    private var alert2 =
        MyAlert(2, "03 Apr, 2023", "19 Apr, 2023", "Mon Apr 03 07:05:48 GMT+02:00 2023")
    private var alert1 =
        MyAlert(1, "02 Apr, 2023", "27 Apr, 2023", "Sun Apr 02 23:55:04 GMT+02:00 2023")
    private var alert3=
        MyAlert(3, "03 Apr, 2023", "19 Apr, 2023", "Mon Apr 03 07:05:48 GMT+02:00 2023")
    var alertList = mutableListOf<MyAlert>()

    @Before
    fun setUp() {
        repo = FakeRepository(FakeLocalSource(alertList = alertList), FakeWeatherClient(myResponse))
        alertsViewModel = AlertsViewModel(repo)

    }

    @Test
    fun insertAlert_addTwoAlerts_True() = mainRule.runBlockingTest {
        //when:call insertAlert function
        alertsViewModel.insertAlert(alert1)
        alertsViewModel.insertAlert(alert2)

        //assert: check the first item in list
        alertsViewModel.getAlerts()
        assertThat(alertList.indexOf(alert1), `is`(0))
        assertThat(alertList.size, `is`(2))
    }


    @Test
    fun deleteAlert_deleteOneLocationFromListWith3Locations_ListSizeEqual2() = runBlocking {
        //given insert Items
        alertsViewModel.insertAlert(alert1)
        alertsViewModel.insertAlert(alert2)
        alertsViewModel.insertAlert(alert3)

        //when call deleteAlert function
        alertsViewModel.deleteAlert(alert2)

        //then return true
        alertsViewModel.getAlerts()
        assertThat(alertList[0], `is`(alert1))
        assertThat(alertList[1].startDate, `is`(alert3.startDate))
        assertThat(alertList.size, `is`(2))

    }

//    @ExperimentalCoroutinesApi
//    @Before
//    fun setUp(){
//        Dispatchers.setMain(testDispatcher)
//
//    }
//
//    @ExperimentalCoroutinesApi
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//
//    }


    //    @ExperimentalCoroutinesApi
//    val testDispatcher : TestCoroutineDispatcher = TestCoroutineDispatcher()

}
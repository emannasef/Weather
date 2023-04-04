package eg.gov.iti.jets.mad.weather.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.gov.iti.jets.mad.weather.MainRule
import eg.gov.iti.jets.mad.weather.database.FakeLocalSource
import eg.gov.iti.jets.mad.weather.network.FakeWeatherClient
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    var rule = MainRule()

    lateinit var repository: Repository

    private var fav1 = FavLocation(latitude = 56.39142787180499, longitude = 36.95064045488834)
    private var fav2 = FavLocation(latitude = 59.01796916246643, longitude = 48.536103665828705)
    private var fav3 = FavLocation(latitude = 59.01796916246643, longitude = 48.536103665828705)

    private var alert1 =
        MyAlert(1, "02 Apr, 2023", "27 Apr, 2023", "Sun Apr 02 23:55:04 GMT+02:00 2023")
    private var alert2 = MyAlert(2, "03 Apr, 2023", "19 Apr, 2023", "Mon Apr 03 07:05:48 GMT+02:00 2023")
    private var alert3 =
        MyAlert(3, "03 Apr, 2023", "27 Apr, 2023", "Mon Apr 03 08:10:43 GMT+02:00 2023")
    private var alert4 =
        MyAlert(4, "03 Apr, 2023", "03 Apr, 2023", "Mon Apr 03 08:18:01 GMT+02:00 2023")

    private val favList = mutableListOf<FavLocation>()
    private val alertList = mutableListOf<MyAlert>()
    var myResponse = MyResponse()
    // lateinit var localSource:FakeLocalSource

    var weather1=BackupModel(1,MyResponse())
    var weather2=BackupModel(2, MyResponse())


    @Before
    fun setUp() {

        //Given : Create object from the Repository to test all methods using fake local and remote sources

        repository = Repository(FakeLocalSource(favList, alertList,weather1), FakeWeatherClient(myResponse))
    }


    @Test
    fun getDataOverNetwork_CompareTheResponseComesFromAPI_WithMyResponse() = runTest {
        //when: call getDataOverNetwork method
        val result = repository.getDataOverNetwork(lat = 0.0, lon = 0.0, language = "en")

        //then: check that the data over network like the Model dataClass
        //note I used (.first or .last ) to get the first element emitted by the flow as it have one element which is MyResponse dataClass
        assertThat(result.first(), `is`(myResponse))

    }


    @Test
    fun insertLocation_addOneFavLocation_FavListNotNull() = runBlockingTest {
        //when:call insertLocation
        repository.insertLocation(fav1)

        //then: check that favorite list is not null
        assertThat(favList.size, `is`(notNullValue()))
    }

    @Test
    fun insertLocation_addTwoFavLocation_FavListEqual3() = runBlockingTest {
        //when:call insertLocation
        repository.insertLocation(fav1)
        repository.insertLocation(fav2)
        repository.insertLocation(fav3)

        //then: size of favorite list incremented by 1
        assertThat(favList.size, `is`(3))
    }



    @Test
    fun deleteLocation_deleteFavLocation_True()= runBlockingTest {
        //given: insert location on fav location to insure the delete is correct
        repository.insertLocation(fav2)

        //when : call deleteLocation function
        repository.deleteLocation(fav2)

        //assert: if size is zero it's mean tha added element is removed successfully
        assertThat(favList.size, `is`(0))
    }
    @Test
    fun getFavLocations_CompareFavListSizeWithTheFlowOfData_True() =runBlockingTest {
        //when:call getFavLocations function
        val result = repository.getFavLocations()

        //assert:
        assertThat(result.first(), `is`(favList))
    }

    @Test
    fun getDataForWorkManger_returnTrueIfFounded() = runBlockingTest {
        //when:call getDataForWorkManger
        val result = repository.getDataForWorkManger(0.0, 0.0, "en")

        //then true if the same response founded
        assertThat(result, `is`(myResponse))
    }



    @Test
    fun insertAlert_add3Alerts_AlertListEqual3() = runBlockingTest {
        //when:call insertAlert
        repository.insertAlert(alert1)
        repository.insertAlert(alert2)
        repository.insertAlert(alert3)

        //then: size of alert list
        assertThat(alertList.size, `is`(3))
    }


    @Test
    fun getAlerts() = runBlockingTest {
        //val alerts = mutableListOf<MyAlert>(alert1,alert2,alert3,alert4)

        //when:call getAlerts function
        val result = repository.getAlerts()

        //assert:
        assertThat(result.first(), `is`(alertList))
    }


    @Test
    fun deleteAlert_deleteAlert_True()= runBlockingTest {
        //given: insert Alert on AlertList to insure the delete is correct
        repository.insertAlert(alert4)

        //when : call deleteAlert function
        repository.deleteAlert(alert4)

        //assert: if size is zero it's mean tha added element is removed successfully
        assertThat(alertList.size, `is`(0))
    }

    @Test
    fun insertDataToBackup_insertLastUpdate()=runBlockingTest {
        //Given insert
        repository.insertDataToBackup(weather1)
        //when
        val response=repository.getBackupData()
        //then true if inserted
       assertThat(response.first(), `is`(weather1))
    }

    @Test
    fun getBackupData_True()=runBlockingTest {
        //Given insert item
        repository.insertDataToBackup(weather2)
        //when request item
        val response=repository.getBackupData()
        //then True
        assertThat(response.count(), `is`(1))
    }


}
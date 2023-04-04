package eg.gov.iti.jets.mad.weather.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import eg.gov.iti.jets.mad.weather.model.MyAlert
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.notNullValue


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class ConcreteLocalSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    var rule = MainRule()

    private lateinit var concreteLocalSource: ConcreteLocalSource
    private lateinit var database: WeatherDatabase
    private var fav1 = FavLocation(latitude = 56.39142787180499, longitude = 36.95064045488834)
    private var fav2 = FavLocation(latitude = 59.01796916246643, longitude = 48.536103665828705)

    private var alert1 =
        MyAlert(1, "02 Apr, 2023", "27 Apr, 2023", "Sun Apr 02 23:55:04 GMT+02:00 2023")
    private var alert2 =
        MyAlert(2, "03 Apr, 2023", "19 Apr, 2023", "Mon Apr 03 07:05:48 GMT+02:00 2023")


    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        concreteLocalSource= ConcreteLocalSource(ApplicationProvider.getApplicationContext())
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun getFavLocations_UpdateRoomList_Size2() = runBlocking {
        //given:insert items
        concreteLocalSource.insertLocation(fav1)
        concreteLocalSource.insertLocation(fav2)
        //when : call getFavLocations()
        val result =  concreteLocalSource.getFavLocations().first()
        //then
        Assert.assertThat(result.size, `is`(notNullValue()))

    }

    @Test
    fun insertAlert_addAlertToDataBase_TrueInserted() = runBlockingTest {
        //when : call method insertAlert
        concreteLocalSource.insertAlert(alert1)

        //assert: data inserted compare lat an lon to insure that insert true
        val result = concreteLocalSource.getAlerts().first()
        Assert.assertThat(result.size, `is`(1))
        Assert.assertThat(result.get(0).endDate, `is`(alert1.endDate))
        Assert.assertThat(result.get(0).startDate, `is`(alert1.startDate))
        Assert.assertThat(result.get(0).pickedTime, `is`(alert1.pickedTime))
    }


    @Test
    fun deleteLocation_deleteOneLocationFromListWith2Locations_ListSizeEqual1() = runBlocking {
        //given: insert 2 Locations
       concreteLocalSource.insertLocation(fav1)
        concreteLocalSource.insertLocation(fav2)

        //when : call method deleteLocation
        concreteLocalSource.deleteLocation(fav2)

        //assert: favList is empty
        val result1 = concreteLocalSource.getFavLocations().first()
        Assert.assertThat(result1.get(0).longitude, `is`(fav1.longitude))
        Assert.assertThat(result1.contains(fav2), `is`(false))
        Assert.assertThat(result1.contains(fav1), `is`(true))
        Assert.assertThat(result1.size, `is`(1))

    }


}

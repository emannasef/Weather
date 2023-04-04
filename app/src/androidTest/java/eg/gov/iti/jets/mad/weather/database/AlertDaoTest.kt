package eg.gov.iti.jets.mad.weather.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import eg.gov.iti.jets.mad.weather.model.MyAlert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlertDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WeatherDatabase
    lateinit var alertDao: AlertDao

    private var alert1 =
        MyAlert(1, "02 Apr, 2023", "27 Apr, 2023", "Sun Apr 02 23:55:04 GMT+02:00 2023")
    private var alert2 =
        MyAlert(2, "03 Apr, 2023", "19 Apr, 2023", "Mon Apr 03 07:05:48 GMT+02:00 2023")



    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), WeatherDatabase::class.java
        ).build()
        alertDao = database.getAlertDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun getAlerts_UpdateRoomList_SizeEqual2() = runBlocking {
        //given:insert items
        alertDao.insertAlert(alert1)
        alertDao.insertAlert(alert2)
        //when : call getAlerts method
        val result = alertDao.getAlerts().first()
        //then
        Assert.assertThat(result.size,`is`(2))

    }

    @Test
    fun insertAlert_addAlertToDataBase_TrueInserted() = runBlockingTest {
        //when : call method insertAlert
        alertDao.insertAlert(alert1)

        //assert: data inserted compare lat an lon to insure that insert true
        val result = alertDao.getAlerts().first()
        Assert.assertThat(result.size, `is`(1))
        Assert.assertThat(result.get(0).endDate, `is`(alert1.endDate))
        Assert.assertThat(result.get(0).startDate, `is`(alert1.startDate))
        Assert.assertThat(result.get(0).pickedTime, `is`(alert1.pickedTime))
    }

    @Test
    fun deleteLocation_deleteTheOnlyLocationFromList_EmptyList() = runBlockingTest {
        //when : call method deleteAlert
        alertDao.insertAlert(alert1)
        alertDao.deleteAlert(alert1)

        //assert: alertList is empty
        val result = alertDao.getAlerts().first()
        Assert.assertThat(result, `is`(emptyList()))
        Assert.assertThat(result.size,`is`(0))
    }

}
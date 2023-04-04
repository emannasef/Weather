package eg.gov.iti.jets.mad.weather.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import eg.gov.iti.jets.mad.weather.MainRule
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import android.content.Context
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.notNullValue


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class ConcreteLocalSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule = MainRule()

    private lateinit var concreteLocalSource: ConcreteLocalSource
    private lateinit var database: WeatherDatabase
    private var fav1 = FavLocation(latitude = 56.39142787180499, longitude = 36.95064045488834)
    private var fav2 = FavLocation(latitude = 59.01796916246643, longitude = 48.536103665828705)


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


}

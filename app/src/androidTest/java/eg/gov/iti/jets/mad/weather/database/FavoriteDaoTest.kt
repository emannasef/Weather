package eg.gov.iti.jets.mad.weather.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: WeatherDatabase
    lateinit var favoriteDao: FavoriteDao
    private var favLoc1 = FavLocation(latitude = 56.39142787180499, longitude = 36.95064045488834)
    private var favLoc2 = FavLocation(latitude = 59.01796916246643, longitude = 48.536103665828705)
    private var favLoc3 = FavLocation(latitude = 59.01796916244366, longitude = 41.536103665828705)

    //private var favList = mutableListOf<FavLocation>(favLoc1, favLoc2, favLoc3)

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), WeatherDatabase::class.java
        ).build()
        favoriteDao = database.getFavoriteDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun getFavLocations_UpdateRoomList_Size2() = runBlocking {
        //given:insert items
        favoriteDao.insertLocation(favLoc1)
        favoriteDao.insertLocation(favLoc2)
        //when : call getFavLocations()
        val result = favoriteDao.getFavLocations().first()
        //then
        assertThat(result.size, `is`(2))

    }

    @Test
    fun insertLocation_addFavLocationToDataBase_TrueInserted() = runBlockingTest {
        //when : call method insertLocation
        favoriteDao.insertLocation(favLoc1)

        //assert: data inserted compare lat an lon to insure that insert true
        val result = favoriteDao.getFavLocations().first()
        assertThat(result.size, `is`(1))
        assertThat(result.get(0).latitude, `is`(favLoc1.latitude))
        assertThat(result.get(0).longitude, `is`(favLoc1.longitude))
        assertThat(result.get(0).address, `is`(favLoc1.address))
    }

    @Test
    fun deleteLocation_deleteTheOnlyLocationFromList_EmptyList() = runBlockingTest {
        //when : call method deleteLocation
        favoriteDao.deleteLocation(favLoc1)

        //assert: favList is empty
        val result = favoriteDao.getFavLocations().first()
        assertThat(result, `is`(emptyList()))
        assertThat(result.size, `is`(0))
    }

    @Test
    fun deleteLocation_deleteOneLocationFromListWith3Locations_ListSizeEqual2() = runBlocking {
        //given: insert 2 Locations
        favoriteDao.insertLocation(favLoc1)
        favoriteDao.insertLocation(favLoc3)
        favoriteDao.insertLocation(favLoc2)

        //when : call method deleteLocation
        favoriteDao.deleteLocation(favLoc2)

        //assert: favList is empty
        val result1 = favoriteDao.getFavLocations().first()
        assertThat(result1.get(0).longitude, `is`(favLoc1.longitude))
        assertThat(result1.contains(favLoc2), `is`(false))
        assertThat(result1.contains(favLoc1), `is`(true))
        assertThat(result1.contains(favLoc3), `is`(true))
        assertThat(result1.size, `is`(2))

    }

    @Test
    fun deleteLocation_deleteOneLocationFromListWith2Locations_True() = runBlocking {
        //given inset Items
        favoriteDao.insertLocation(favLoc1)
        favoriteDao.insertLocation(favLoc2)

        //when call deleteLocation function
        favoriteDao.deleteLocation(favLoc1)

        //then return true
        val result = favoriteDao.getFavLocations().first()
        assertThat(result .get(0).longitude, `is`(favLoc2.longitude))
        assertThat(result .get(0).latitude, `is`(favLoc2.latitude))

    }

}
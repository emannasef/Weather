package eg.gov.iti.jets.mad.weather.viewModel.fav

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.gov.iti.jets.mad.weather.MainRule
import eg.gov.iti.jets.mad.weather.database.FakeLocalSource
import eg.gov.iti.jets.mad.weather.model.*
import eg.gov.iti.jets.mad.weather.network.FakeWeatherClient
import eg.gov.iti.jets.mad.weather.viewModel.alert.AlertsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FavViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainRule = MainRule()


    private lateinit var repo: RepositoryInterface
    lateinit var viewModel: FavViewModel
    private var myResponse = MyResponse()


    private var fav1 = FavLocation(latitude = 56.39142787180499, longitude = 36.95064045488834)
    private var fav2 = FavLocation(latitude = 59.01796916246643, longitude = 48.536103665828705)
    private var fav3 = FavLocation(latitude = 59.01796916244366, longitude = 41.536103665828705)
    private val favList: MutableList<FavLocation> = mutableListOf()

    @Before
    fun setUp() {
        repo = FakeRepository(FakeLocalSource(favList), FakeWeatherClient(myResponse))
        viewModel = FavViewModel(repo)

    }


    @Test
    fun insertFavorite_add2Location_True() = runBlockingTest {

        //when:call insertFavorite function
        viewModel.insertFavorite(fav1)
        viewModel.insertFavorite(fav2)
        viewModel.insertFavorite(fav3)

        //assert: check size of list
        viewModel.getFavLocations()
        assertThat(favList.size, `is`(3))
    }



    @Test
    fun deleteFromFav_deleteTheOnlyLocationFromList_EmptyList() = mainRule.runBlockingTest {  //Given
       //given:insert fav location to delete it
        viewModel.insertFavorite(fav2)

        //when removed from db
        viewModel.deleteFromFav(fav2)

        //then check if deleted
        viewModel.getFavLocations()
        assertThat(favList, `is`(emptyList<FavLocation>()))
    }

    @Test
    fun deleteFromFav_deleteOneLocationFromListWith3Locations_ListSizeEqual2() = runBlocking {
        //given insert Items
        viewModel.insertFavorite(fav1)
        viewModel.insertFavorite(fav2)
        viewModel.insertFavorite(fav3)

        //when call deleteLocation function
        viewModel.deleteFromFav(fav2)

        //then return true
        viewModel.getFavLocations()
        assertThat(favList[0], `is`(fav1))
        assertThat(favList[1].latitude, `is`(fav3.latitude))
        assertThat(favList.size, `is`(2))

    }

}
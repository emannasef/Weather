package eg.gov.iti.jets.mad.weather.viewModel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.gov.iti.jets.mad.weather.MainRule
import eg.gov.iti.jets.mad.weather.database.FakeLocalSource
import eg.gov.iti.jets.mad.weather.model.FakeRepository
import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface
import eg.gov.iti.jets.mad.weather.network.FakeWeatherClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainRule = MainRule()


    @Test
    fun getWeatherOverNetwork() {

        //Given: Create object from HomeViewMode --> to da that bwe need to create an object of Fake FakeRepository
        val myResponse = MyResponse()
        val repo = FakeRepository(FakeLocalSource(),FakeWeatherClient(myResponse))
        val viewModel = HomeViewModel(repo)

        //when: Call getWeatherOverNetwork function
        val result = viewModel.getWeatherOverNetwork(0.0, 0.0, "en")

        //assert: check that it's not null
        assertThat(result, `is`(notNullValue()))
        assertThat(result, notNullValue())

    }
}
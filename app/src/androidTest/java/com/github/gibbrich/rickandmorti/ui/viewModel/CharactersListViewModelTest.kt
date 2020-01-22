package com.github.gibbrich.rickandmorti.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.github.gibbrich.rickandmorti.AppTest
import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.core.repository.CharactersRepository
import com.github.gibbrich.rickandmorti.di.AppComponentMock
import com.github.gibbrich.rickandmorti.di.DI
import com.github.gibbrich.rickandmorti.utils.MainCoroutineScopeRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class CharactersListViewModelTest {
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val app by lazy {
        InstrumentationRegistry
            .getInstrumentation()
            .targetContext
            .applicationContext as AppTest
    }

    lateinit var viewModel: CharactersListViewModel

    @Inject
    lateinit var charactersRepository: CharactersRepository

    @Before
    fun setUp() {
        val component = app.provideAppComponent() as AppComponentMock
        DI.init(component)
        component.inject(this)
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun viewModel_charactersCached_returns_empty_list_on_init() = runBlocking {
        whenever(charactersRepository.fetchNextCharacters(any(), any())).then { emptyList<Character>() }
        viewModel = CharactersListViewModel()
        assertTrue(viewModel.charactersCached.isNullOrEmpty())
    }

    @Test
    fun viewModel_loadingState_start_null_success_fetchCharactersPage_LOADING_end_null() = runBlocking {
        val list = listOf(Character(1, "Rick", "S01E01", "some url", "Earth"))
        whenever(charactersRepository.fetchNextCharacters(any(), any())).then { list }
        viewModel = CharactersListViewModel()

        var emitCount = 0

        val observer = Observer<LoadingState?> {
            emitCount++

            when (emitCount) {
                1 -> assertNull(it)
                2 -> assertTrue(it == LoadingState.LOADING)
                3 -> assertNull(it)
            }
        }

        viewModel.loadingState.observeForever(observer)

        viewModel.fetchCharactersPage()

        assertEquals(3, emitCount)
    }
}
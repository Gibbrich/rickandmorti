package com.github.gibbrich.rickandmorti.ui.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.gibbrich.rickandmorti.R
import com.github.gibbrich.rickandmorti.adapter.CharactersAdapter
import com.github.gibbrich.rickandmorti.adapter.FooterState
import com.github.gibbrich.rickandmorti.adapter.ViewHolderListener
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.di.DI
import com.github.gibbrich.rickandmorti.ui.viewModel.CharactersListViewModel
import com.github.gibbrich.rickandmorti.ui.viewModel.LoadingState
import kotlinx.android.synthetic.main.characters_list_fragment.*
import javax.inject.Inject

class CharacterListFragment : Fragment() {
    @Inject
    lateinit var navigationManager: INavigationManager

    private val viewModel: CharactersListViewModel by viewModels()
    private var adapter: CharactersAdapter? = null
    private var characterTransitionUrl: String? = null

    private val viewHolderListenerDelegate = object : ViewHolderListener {
        override fun onLoadCompleted(model: String) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            if (characterTransitionUrl == model) {
                startPostponedEnterTransition()
            }
        }

        override fun onItemClicked(view: View, character: Character) {
            // Update the position.
            characterTransitionUrl = character.photoUrl

            // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
            // instead of fading out with the rest to prevent an overlapping animation of fade and move).
            (exitTransition as TransitionSet).excludeTarget(view, true)

            navigationManager.switchToCharacterDetailScreen(view, character)
        }

        override fun onRetry() {
            viewModel.fetchCharactersPage()
        }
    }

    init {
        DI.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.characters_list_fragment, container, false)

        exitTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition)

        if (characterTransitionUrl != null) {
            postponeEnterTransition()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.charactersPage.observe(this, Observer(::handleCharacters))
        viewModel.loadingState.observe(this, Observer(::handleLoadingState))

        val layoutManager = LinearLayoutManager(activity)
        activity_main_characters_list.layoutManager = layoutManager

        if (adapter == null) {
            // on fragment recreation we don't need subscription, so we just repopulate
            // cached data
            adapter = CharactersAdapter(
                viewHolderListenerDelegate,
                Glide.with(this),
                viewModel.charactersCached.toMutableList() // important to perform deep copy to avoid double data, as viewModel.charactersCached populate during work
            )
        }
        activity_main_characters_list.adapter = adapter
        setRecyclerViewScrollListener(activity_main_characters_list, layoutManager, adapter!!)
    }

    private fun handleLoadingState(state: LoadingState?) {
        val footerState = when (state) {
            LoadingState.LOADING -> FooterState.LOADING
            LoadingState.ERROR -> FooterState.ERROR
            null -> null
        }
        adapter?.updateFooterState(footerState)
    }

    private fun handleCharacters(characters: List<Character>) {
        adapter?.addItems(characters)
    }

    private fun setRecyclerViewScrollListener(
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager,
        adapter: CharactersAdapter
    ) = recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val shouldFetchCharacters =
                adapter.footerState == null && layoutManager.itemCount == lastVisibleItemPosition + 1
            if (shouldFetchCharacters) {
                viewModel.fetchCharactersPage()
            }
        }
    })
}

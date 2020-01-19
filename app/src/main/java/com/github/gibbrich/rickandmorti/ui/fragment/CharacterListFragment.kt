package com.github.gibbrich.rickandmorti.ui.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.github.gibbrich.rickandmorti.R
import com.github.gibbrich.rickandmorti.adapter.CharactersAdapter
import com.github.gibbrich.rickandmorti.adapter.ViewHolderListener
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.core.model.Character
import com.github.gibbrich.rickandmorti.di.DI
import com.github.gibbrich.rickandmorti.ui.viewModel.CharactersListViewModel
import kotlinx.android.synthetic.main.characters_list_fragment.*
import javax.inject.Inject

class CharacterListFragment : Fragment(), ViewHolderListener {
    companion object {
        fun newInstance() = CharacterListFragment()
    }

    @Inject
    lateinit var navigationManager: INavigationManager

    private lateinit var viewModel: CharactersListViewModel
    private lateinit var adapter: CharactersAdapter
    private var characterTransitionUrl: String? = null

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
        viewModel = ViewModelProviders.of(this).get(CharactersListViewModel::class.java)

        viewModel.viewState.observe(this, Observer(::handleState))

        characters_list_fragment_swipe_layout.setOnRefreshListener(viewModel::fetchCharacters)

        activity_main_characters_list.layoutManager = getGridLayoutManager()
        adapter = CharactersAdapter(fragment = this)
        activity_main_characters_list.adapter = adapter
    }

    private fun handleState(state: CharactersListViewModel.State) = when (state) {
        CharactersListViewModel.State.LoadError -> {
            characters_list_fragment_swipe_layout.isRefreshing = false
        }

        CharactersListViewModel.State.Loading -> {
            characters_list_fragment_swipe_layout.isRefreshing = true
        }

        CharactersListViewModel.State.Empty -> {
            activity_main_empty_label.text = getString(R.string.activity_main_no_data)
            activity_main_empty_label.visibility = View.VISIBLE
            activity_main_characters_list.visibility = View.GONE
            characters_list_fragment_swipe_layout.isRefreshing = false
        }

        is CharactersListViewModel.State.Loaded -> {
            activity_main_empty_label.visibility = View.GONE
            activity_main_characters_list.visibility = View.VISIBLE
            adapter.addDataToStart(state.characters)
            characters_list_fragment_swipe_layout.isRefreshing = false
        }
    }

    private fun getGridLayoutManager(): GridLayoutManager {
        val glm = GridLayoutManager(activity, 3)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position % 3 == 2) {
                    return 3
                }
                return when (position % 4) {
                    1, 3 -> 1
                    0, 2 -> 2
                    else ->
                        //never gonna happen
                        -1
                }
            }
        }
        glm.spanSizeLookup.isSpanIndexCacheEnabled = true
        return glm
    }

    override fun onLoadCompleted(view: ImageView) {
        // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
        if (characterTransitionUrl == view.transitionName) {
            startPostponedEnterTransition()
        }
    }

    /**
     * Handles a view click by setting the current position to the given `position` and
     * starting a [ImagePagerFragment] which displays the image at the position.
     *
     * @param view the clicked [ImageView] (the shared element view will be re-mapped at the
     * GridFragment's SharedElementCallback)
     * @param position the selected view position
     */
    override fun onItemClicked(view: View, character: Character) {
        // Update the position.
        characterTransitionUrl = character.photoUrl

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        (exitTransition as TransitionSet).excludeTarget(view, true)

        navigationManager.switchToCharacterDetailScreen(view, character)
    }
}

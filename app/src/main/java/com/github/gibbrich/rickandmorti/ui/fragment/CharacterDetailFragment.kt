package com.github.gibbrich.rickandmorti.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.github.gibbrich.rickandmorti.R
import com.github.gibbrich.rickandmorti.core.model.Character
import kotlinx.android.synthetic.main.fragment_character_detail.view.*

class CharacterDetailFragment : Fragment() {

    companion object {
        private const val TAG = "CharacterDetailFragment"
        const val CHARACTER_KEY = "$TAG.CHARACTER_KEY"

        fun getParams(character: Character) = Bundle().apply { putParcelable(CHARACTER_KEY, character) }
    }

    private val character by lazy {
        arguments?.getParcelable<Character>(CHARACTER_KEY) ?: throw Exception("No Character passed as argument")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_character_detail, container, false)

        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }

        root.fragment_character_detail_character_image.transitionName = character.photoUrl
        root.fragment_character_detail_character_name.text = character.name
        root.fragment_character_detail_character_origin.text = character.origin
        root.fragment_character_detail_character_first_episode.text = character.firstEpisode

        Glide.with(this)
            .load(character.photoUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                    // startPostponedEnterTransition() should also be called on it to get the transition
                    // going in case of a failure.
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                    // startPostponedEnterTransition() should also be called on it to get the transition
                    // going when the image is ready.
                    startPostponedEnterTransition()
                    return false
                }
            })
            .error(R.drawable.ic_broken_image_black_24dp)
            .into(root.fragment_character_detail_character_image)


        return root
    }
}

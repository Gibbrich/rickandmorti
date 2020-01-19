package com.github.gibbrich.rickandmorti.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.gibbrich.rickandmorti.R
import com.github.gibbrich.rickandmorti.core.model.Character
import kotlinx.android.synthetic.main.layout_list_characters_item.view.*

class CharactersAdapter(
    items: MutableList<Character> = mutableListOf(),
    fragment: Fragment
) : ConstantValueAdapter<Character, CharactersAdapter.Holder>(items) {

    private val requestManager: RequestManager = Glide.with(fragment)
    private val viewHolderListener = fragment as ViewHolderListener

    override fun createHolder(view: View): Holder =
        Holder(
            view,
            view.list_characters_item_character_image
        )

    override val lineResourceId = R.layout.layout_list_characters_item

    /**
     * Binds this view holder to the given adapter position.
     *
     * The binding will load the image into the image view, as well as set its transition name for
     * later.
     */
    override fun bind(holder: Holder, item: Character, position: Int) {
        // Load the image with Glide to prevent OOM error when the image drawables are very large.
        val loadListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                viewHolderListener.onLoadCompleted(holder.picture)
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                viewHolderListener.onLoadCompleted(holder.picture)
                return false
            }
        }

        requestManager
            .load(item.photoUrl)
            .listener(loadListener)
            .into(holder.picture)

        // Set the string value of the image resource as the unique transition name for the view.
        holder.picture.transitionName = item.photoUrl
        holder.picture.setOnClickListener {
            viewHolderListener.onItemClicked(it, item)
        }
    }

    class Holder(
        view: View,
        val picture: ImageView
    ) : RecyclerView.ViewHolder(view)
}

/**
 * A listener that is attached to all ViewHolders to handle image loading events and clicks.
 */
interface ViewHolderListener {

    fun onLoadCompleted(view: ImageView)

    fun onItemClicked(view: View, character: Character)
}
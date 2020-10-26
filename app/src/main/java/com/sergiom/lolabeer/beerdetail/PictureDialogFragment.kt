package com.sergiom.lolabeer.beerdetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.sergiom.lolabeer.R
import com.squareup.picasso.Picasso
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * Dialog Fragment with the beer image. This image can be zoomed
 */
class PictureDialogFragment: DialogFragment() {

    private lateinit var picture: String
    private lateinit var imageView: ImageView
    private var photoViewAttacher: PhotoViewAttacher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        picture = bundle?.getString("picture")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.picture_dialog_fragment, container, false)
        imageView = view.findViewById(R.id.beer_image_zoom)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(picture).into(imageView)
        photoViewAttacher = PhotoViewAttacher(imageView) //This will do the zoom trick
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }
}
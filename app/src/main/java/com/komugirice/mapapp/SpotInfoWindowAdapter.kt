package com.komugirice.mapapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import com.komugirice.mapapp.databinding.InfoWindowLayoutBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.komugirice.mapapp.extension.visible
import com.squareup.picasso.Picasso

/**
 * @author Jane
 */
class SpotInfoWindowAdapter(private val activity: Activity?, spotIds: List<Long>): GoogleMap.InfoWindowAdapter {

    private val infoWindowLayoutBinding: InfoWindowLayoutBinding =
        InfoWindowLayoutBinding.inflate(LayoutInflater.from(activity), null, false)

    private val imageLoadedMap = mutableMapOf<Long, Boolean>()

    init {
        spotIds.forEach {
            imageLoadedMap[it] = false
        }
    }

    override fun getInfoContents(marker: Marker?): View {
        return View(activity)
    }

    override fun getInfoWindow(tempMarker: Marker?): View {
        val marker = tempMarker ?: return View(activity)
        var spot = marker.tag as? ImageData ?: return View(activity)
        if (imageLoadedMap[spot.id] == true) {
            infoWindowLayoutBinding.apply {
                progressImageView.visible(false)
            }
            Picasso.get().load(spot.filePath).into(infoWindowLayoutBinding.spotImageView)
        } else {
            imageLoadedMap[spot.id] = true
            infoWindowLayoutBinding.apply {
                progressImageView.visible(true)
            }
            Picasso.get().load(spot.filePath)
                .into(infoWindowLayoutBinding.spotImageView, InfoWindowRefresher(marker))
        }
        return infoWindowLayoutBinding.root
    }
}
package com.komugirice.mapapp.ui.preference


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.evernote.client.android.EvernoteSession
import com.komugirice.mapapp.MyApplication.Companion.isEvernoteLoggedIn
import com.komugirice.mapapp.R
import kotlinx.android.synthetic.main.fragment_preference.*

class PreferenceFragment: Fragment() {

    private lateinit var preferenceViewModel: PreferenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferenceViewModel =
            ViewModelProviders.of(this).get(PreferenceViewModel::class.java).apply {

                mode.observe(this@PreferenceFragment, Observer {
                    modeValue.text = it
                })
                evernoteName.observe(this@PreferenceFragment, Observer {
                    evernoteValue.text = it
                })
            }
        val root = inflater.inflate(R.layout.fragment_preference, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClick()
    }

    private fun initClick(){
        evernoteValue.setOnClickListener {
            if(!isEvernoteLoggedIn)
                EvernoteSession.getInstance().authenticate(this.activity)
        }
    }

}

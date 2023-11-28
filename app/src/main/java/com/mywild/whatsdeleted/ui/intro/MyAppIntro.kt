package com.mywild.whatsdeleted.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.mywild.whatsdeleted.R
import com.mywild.whatsdeleted.ui.main.MainActivity
import com.mywild.whatsdeleted.utility.finishedIntro
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAppIntro : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showStatusBar(false)
        //Set full screen
        setImmersiveMode()
        //Hide skip button
        isWizardMode = true

        //Adding slides
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_layout_one))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_layout_two))
        addSlide(IntroPermissionFragment())

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this,MainActivity::class.java))
        finishedIntro()
        finish()
    }
}
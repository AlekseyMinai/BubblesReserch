package com.alesno.bubblebuttonreserch.ui

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.ActivityMainBinding
import com.alesno.bubblebuttonreserch.ui.conversation.ConversationFragment
import com.alesno.bubblebuttonreserch.ui.conversationlist.ConversationListFragment
import com.alesno.bubblebuttonreserch.viewBindings


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mBinding by viewBindings(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            requestBubblePermissions()
//        }
//        mBinding.button.setOnClickListener {
//            BubbleNotificationManager.createNotificationChannel(this)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                BubbleNotificationManager.createNotification(this)
//            }
//        }
        //openConversationFragment()
        openConversationListFragment()
    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentByTag(CONVERSATION_LIST_FRAGMENT_TAG) as? ConversationListFragment
        if (fragment?.onBackPressed() == true) {
            return
        }
        if (fragment != null) {
            finish()
        }
        super.onBackPressed()
    }

    private fun openConversationListFragment() {
        if (supportFragmentManager.findFragmentByTag(CONVERSATION_LIST_FRAGMENT_TAG) != null) {
            return
        }
        supportFragmentManager.commit {
            add(
                R.id.fragmentContainer,
                ConversationListFragment.newInstance(),
                CONVERSATION_LIST_FRAGMENT_TAG
            ).addToBackStack(CONVERSATION_LIST_FRAGMENT_TAG)
        }
    }

    private fun openConversationFragment() {
        if (supportFragmentManager.findFragmentByTag(CONVERSATION_FRAGMENT_TAG) != null) {
            return
        }
        supportFragmentManager.commit {
            replace(
                R.id.fragmentContainer,
                ConversationFragment.newInstance("1", false),
                CONVERSATION_FRAGMENT_TAG
            ).addToBackStack(CONVERSATION_FRAGMENT_TAG)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun canDisplayBubbles(): Boolean {
        val bubblesEnabledGlobally = try {
            Settings.Global.getInt(contentResolver, "notification_bubbles") == 1
        } catch (e: SettingNotFoundException) {
            true
        }
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        return bubblesEnabledGlobally && notificationManager.areBubblesAllowed()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBubblePermissions() {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_BUBBLE_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivityForResult(intent, REQUEST_CODE_BUBBLES_PERMISSION)
    }

    companion object {
        private const val REQUEST_CODE_BUBBLES_PERMISSION = 1212
        private const val CONVERSATION_FRAGMENT_TAG = "conversation_fragment"
        private const val CONVERSATION_LIST_FRAGMENT_TAG = "conversation_list_fragment"
    }

}
package com.alesno.bubblebuttonreserch.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alesno.bubblebuttonreserch.BubbleNotificationManager
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.FragmentSettingsBinding
import com.alesno.bubblebuttonreserch.domain.FakeDataRepo
import com.alesno.bubblebuttonreserch.domain.Settings
import com.alesno.bubblebuttonreserch.viewBindings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val mBinding by viewBindings(FragmentSettingsBinding::bind)
    private val mViewModel by viewModels<SettingsViewModel> { SettingsViewModelFactory.create() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFastChatSetting()
        subscribeToViewModel()
    }

    private fun initFastChatSetting() = with(mBinding) {
        fastChatSetting.setOnCheckedChangeListener { _, isChecked ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && isChecked) {
                requestBubblePermissions()
                BubbleNotificationManager.createNotificationChannel(requireContext())
                lifecycleScope.launch {
                    BubbleNotificationManager.createNotification(
                        context = requireContext(),
                        participant = FakeDataRepo.getParticipant()!!
                    )
                }
            }
        }
        fastChatSetting.setOnClickListener { mViewModel.onFastChatClicked() }
    }

    private fun subscribeToViewModel() {
        mViewModel.settingsState
            .onEach(::render)
            .launchIn(lifecycleScope)
    }

    private fun render(settings: Settings) = with(mBinding) {
        fastChatSetting.isChecked = settings.isFastChatEnable
    }

    // TODO: 6/20/2021 Вынести в другое место
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBubblePermissions() {
        val intent = Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_BUBBLE_SETTINGS)
            .putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, requireActivity().packageName)
        startActivityForResult(intent, REQUEST_CODE_BUBBLES_PERMISSION)
    }

    companion object {
        private const val REQUEST_CODE_BUBBLES_PERMISSION = 1212

        fun newInstance(): SettingsFragment = SettingsFragment()
    }

}
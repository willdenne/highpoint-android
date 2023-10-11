package will.denne.launches.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import will.denne.launches.R
import will.denne.launches.databinding.MainFragmentBinding
import will.denne.launches.ui.main.composable.Navigation

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MainFragmentBinding.bind(view)
        binding.composeView.setContent {
            Navigation()
        }
    }

}
package com.example.jetpackdemo.ui.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.jetpackdemo.databinding.FragmentRegisterBinding
import com.example.jetpackdemo.ui.login.viewmodel.RegisterViewModel
import com.example.jetpackdemo.util.InjectorUtil
import kotlinx.android.synthetic.main.fragment_login.txt_cancel


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels {
        InjectorUtil.getRegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.model = viewModel
        binding.activity = activity
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_cancel.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
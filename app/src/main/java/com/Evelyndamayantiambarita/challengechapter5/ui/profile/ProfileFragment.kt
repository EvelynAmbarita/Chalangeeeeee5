package com.Evelyndamayantiambarita.challengechapter5.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.Evelyndamayantiambarita.challengechapter5.Constant
import com.Evelyndamayantiambarita.challengechapter5.databinding.FragmentProfileBinding
import com.Evelyndamayantiambarita.challengechapter5.ui.login.LoginActivity
import com.Evelyndamayantiambarita.challengechapter5.database.MyDatabase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    val viewModel: ProfileViewModel by viewModels()
    private var db: MyDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLogout.setOnClickListener {
            val pref = this.requireActivity()
                .getSharedPreferences(Constant.Preferences.PREF_NAME, Context.MODE_PRIVATE)
            pref.edit().clear().commit()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        val db = MyDatabase.getInstance(this.requireContext())
        val pref = this.requireActivity()
            .getSharedPreferences(Constant.Preferences.PREF_NAME, Context.MODE_PRIVATE)

        pref.getString(Constant.Preferences.KEY.EMAIL,"")?.let { viewModel.getUser(it) }
        bindViewModel()

        viewModel.onViewLoaded(db,pref)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = MyDatabase.getInstance(requireContext().applicationContext)
    }

    private fun bindViewModel() {
        viewModel.shouldShowDataUser.observe(viewLifecycleOwner) {
           binding.etUsername.setText(it.username).toString()
           binding.etJob.setText(it.job).toString()
           binding.etEmail.setText(it.email).toString()
           binding.etPassword.setText(it.password).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
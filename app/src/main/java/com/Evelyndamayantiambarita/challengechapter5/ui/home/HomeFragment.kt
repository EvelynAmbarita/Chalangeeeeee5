package com.Evelyndamayantiambarita.challengechapter5.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.Evelyndamayantiambarita.challengechapter5.Constant
import com.Evelyndamayantiambarita.challengechapter5.database.MyDatabase
import com.Evelyndamayantiambarita.challengechapter5.databinding.FragmentHomeBinding
import com.Evelyndamayantiambarita.challengechapter5.model.movie.MoviePopulerModel
import com.Evelyndamayantiambarita.challengechapter5.ui.detail.DetailActivity
import com.Evelyndamayantiambarita.challengechapter5.ui.home.adapter.ListGenreAdapter
import com.Evelyndamayantiambarita.challengechapter5.ui.home.adapter.MovieNowPlayingAdapter
import com.Evelyndamayantiambarita.challengechapter5.ui.home.adapter.MoviePopulerAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val viewModel: HomeViewModel by viewModels()
    lateinit var moviePopulerAdapter: MoviePopulerAdapter
    lateinit var movieNowPlayingAdapter: MovieNowPlayingAdapter
    lateinit var listGenreAdapter: ListGenreAdapter
    private var db: MyDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        moviePopulerAdapter =
            MoviePopulerAdapter(listener = object : MoviePopulerAdapter.EventListener {
                override fun onClick(item: MoviePopulerModel) {
                    Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()

                    val intent = Intent(activity, DetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("my_key", item.id.toString())
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }, emptyList())
        binding.rvMoviePopuler.adapter = moviePopulerAdapter

        movieNowPlayingAdapter =
            MovieNowPlayingAdapter(listener = object : MovieNowPlayingAdapter.EventListener {
                override fun onClick(item: MoviePopulerModel) {
                    Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()

                    val intent = Intent(activity, DetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("my_key", item.id.toString())
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }, emptyList())
        binding.rvMovieNowPlaying.adapter = movieNowPlayingAdapter

        listGenreAdapter = ListGenreAdapter(emptyList())
        binding.rvListGenre.adapter = listGenreAdapter

        val db = MyDatabase.getInstance(this.requireContext())
        val pref = this.requireActivity()
            .getSharedPreferences(Constant.Preferences.PREF_NAME, Context.MODE_PRIVATE)

        viewModel.getMoviePopuler()
        viewModel.getListMovie()
        pref.getString(Constant.Preferences.KEY.EMAIL, "")?.let { viewModel.getUser(it) }
        bindViewModel()


        viewModel.onViewLoaded(db, pref)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = MyDatabase.getInstance(requireContext().applicationContext)
    }

    private fun bindViewModel() {
        viewModel.shouldShowMoviePopuler.observe(requireActivity()) {
            moviePopulerAdapter.updateList(it)
        }

        viewModel.shouldShowMoviePopuler.observe(requireActivity()) {
            movieNowPlayingAdapter.updateList(it)
        }

        viewModel.shouldShowListGenre.observe(requireActivity()) {
            listGenreAdapter.updateList(it)
        }

        viewModel.shouldShowUsername.observe(viewLifecycleOwner) {
            binding.tvTitle.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.bedu.clasekotlinavanzado

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Fragment2 : Fragment() {

    override fun onAttach(context: Context) {
        Log.d("Fragment", "onAttach llamado en BETO 2")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Fragment", "onCreate llamado BETO 2")
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("Fragment", "onActivityCreated llamado BETO 2")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d("Fragment", "onStart llamado BETO 2")
        super.onStart()
    }

    override fun onResume() {
        Log.d("Fragment", "onResume llamado BETO 2")
        super.onResume()
    }

    override fun onPause() {
        Log.d("Fragment", "onPause llamado BETO 2")
        super.onPause()
    }

    override fun onStop() {
        Log.d("Fragment", "onStop llamado BETO 2")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("Fragment", "onDestroyView llamado BETO 2")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("Fragment", "onDestroy llamado BETO")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("Fragment", "onDetach llamado BETO")
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false)
    }
}
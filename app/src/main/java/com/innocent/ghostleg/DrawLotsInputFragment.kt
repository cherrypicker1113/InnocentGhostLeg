package com.innocent.ghostleg

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.databinding.DrawLotsInputFragmentBinding

class DrawLotsInputFragment : Fragment() {
    private lateinit var binding: DrawLotsInputFragmentBinding
    private lateinit var listener: DrawLotsInputListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DrawLotsInputFragmentBinding.inflate(inflater, container, false)
        binding.submitButton.setOnClickListener {
            listener.onEnter(Integer.parseInt(binding.input.text.toString()))
        }
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DrawLotsInputListener) {
            listener = context
        }
    }
}

interface DrawLotsInputListener {
    fun onEnter(num: Int)
}
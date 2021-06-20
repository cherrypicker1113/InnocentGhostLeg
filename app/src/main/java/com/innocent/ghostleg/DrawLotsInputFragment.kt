package com.innocent.ghostleg

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.databinding.FragmentDrawLotsInputBinding

const val MAX_MEMBER_COUNT: Int = 8
const val MIN_MEMBER_COUNT: Int = 2
const val MIN_WINNER_COUNT: Int = 1

class DrawLotsInputFragment : Fragment() {
    private lateinit var binding: FragmentDrawLotsInputBinding
    private lateinit var listener: DrawLotsInputListener
    private var memberCount: Int = MIN_MEMBER_COUNT
    private var winnerCount: Int = MIN_WINNER_COUNT
    private lateinit var memberListAdapter: HrgwonAdapter
    private lateinit var winnerListAdapter: HrgwonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawLotsInputBinding.inflate(inflater, container, false)

        initMemberSettingView()
        initWinnerSettingView()

        binding.btnPlay.setOnClickListener {
            listener.onCompleteDrawLotsInput(
                Integer.parseInt(binding.memberCounter.tvNum.text.toString()),
                Integer.parseInt(binding.winnerCounter.tvNum.text.toString())
            )
        }
        return binding.root
    }

    private fun initMemberSettingView() {
        memberListAdapter = HrgwonAdapter(context!!, arrayListOf())
        binding.memberListView.adapter = memberListAdapter

        binding.memberCounter.btnIncrease.setOnClickListener {
            if (memberCount < MAX_MEMBER_COUNT) {
                memberCount++
            }
            updateMemberSettingView()
        }
        binding.memberCounter.btnDecrease.setOnClickListener {
            if (memberCount > MIN_MEMBER_COUNT) {
                memberCount--
            }
            updateMemberSettingView()
        }

        updateMemberSettingView(0)
    }

    private fun initWinnerSettingView() {
        winnerListAdapter = HrgwonAdapter(context!!, arrayListOf())
        binding.winnerListView.adapter = winnerListAdapter

        binding.winnerCounter.tvTitle.text = "당첨수"
        binding.winnerCounter.tvUnit.text = "개"

        binding.winnerCounter.btnIncrease.setOnClickListener {
            if (winnerCount < memberCount - 1) {
                winnerCount++
            }
            updateWinnerSettingView()
        }
        binding.winnerCounter.btnDecrease.setOnClickListener {
            if (winnerCount > MIN_WINNER_COUNT) {
                winnerCount--
            }
            updateWinnerSettingView()
        }

        updateWinnerSettingView(0)
    }

    private fun updateMemberSettingView(beforeCount: Int = memberListAdapter.count) {
        binding.memberCounter.tvNum.text = memberCount.toString()
        if (beforeCount < memberCount) {
            for (i in beforeCount until memberCount)
                memberListAdapter.addItem(Data(i + 1, ""))
        } else if (beforeCount > memberCount) {
            for (i in memberCount until beforeCount)
                memberListAdapter.removeItem()
        }
    }

    private fun updateWinnerSettingView(beforeCount: Int = winnerListAdapter.count) {
        binding.winnerCounter.tvNum.text = winnerCount.toString()
        if (beforeCount < winnerCount) {
            for (i in beforeCount until winnerCount)
                winnerListAdapter.addItem(Data(i + 1, ""))
        } else if (beforeCount > winnerCount) {
            for (i in winnerCount until beforeCount)
                winnerListAdapter.removeItem()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DrawLotsInputListener) {
            listener = context
        }
    }
}

interface DrawLotsInputListener {
    fun onCompleteDrawLotsInput(totalCount: Int, selectCount: Int)
}
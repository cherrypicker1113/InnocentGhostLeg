package com.innocent.ghostleg.drawlots

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.Data
import com.innocent.ghostleg.HrgwonAdapter
import com.innocent.ghostleg.databinding.FragmentDrawLotsInputBinding

const val MAX_MEMBER_COUNT: Int = 8
const val MIN_MEMBER_COUNT: Int = 2
const val MIN_WINNER_COUNT: Int = 1

class DrawLotsInputFragment : Fragment() {
    private lateinit var binding: FragmentDrawLotsInputBinding
    private var listener: DrawLotsInputListener? = null
    private var memberCount: Int = MIN_MEMBER_COUNT
    private var winnerCount: Int = MIN_WINNER_COUNT
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
            listener?.onCompleteDrawLotsInput(
                Integer.parseInt(binding.memberCounter.tvNum.text.toString()),
                (winnerListAdapter.dataList.map { data -> data.name }).toTypedArray()
            )
        }
        return binding.root
    }

    private fun initMemberSettingView() {
        binding.memberCounter.tvTitle.text = "참가 인원"

        binding.memberCounter.btnIncrease.setOnClickListener {
            if (memberCount < MAX_MEMBER_COUNT) {
                memberCount++
                updateMemberSettingView()
            }
        }
        binding.memberCounter.btnDecrease.setOnClickListener {
            if (memberCount > MIN_MEMBER_COUNT) {
                memberCount--
                updateMemberSettingView()
            }
        }

        updateMemberSettingView()
    }

    private fun initWinnerSettingView() {
        winnerListAdapter = HrgwonAdapter(context!!, arrayListOf())
        binding.winnerListView.adapter = winnerListAdapter

        binding.winnerCounter.tvTitle.text = "당첨 인원"

        binding.winnerCounter.btnIncrease.setOnClickListener {
            if (winnerCount < memberCount) {
                winnerCount++
                updateWinnerSettingView()
            }
        }
        binding.winnerCounter.btnDecrease.setOnClickListener {
            if (winnerCount > MIN_WINNER_COUNT) {
                winnerCount--
                updateWinnerSettingView()
            }
        }

        updateWinnerSettingView(0)
    }

    private fun updateMemberSettingView() {
        binding.memberCounter.tvNum.text = memberCount.toString()
    }

    private fun updateWinnerSettingView(beforeCount: Int = winnerListAdapter.count) {
        binding.winnerCounter.tvNum.text = winnerCount.toString()
        if (beforeCount < winnerCount) {
            for (i in beforeCount until winnerCount)
                winnerListAdapter.addItem(Data(i + 1, "당첨${i + 1}"))
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
    fun onCompleteDrawLotsInput(totalCount: Int, winNameList: Array<String>)
}
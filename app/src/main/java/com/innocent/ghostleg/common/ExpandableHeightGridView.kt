package com.innocent.ghostleg.common

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

class ExpandableHeightGridView : GridView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(
            MEASURED_SIZE_MASK,
            MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, expandSpec)
        val params = layoutParams
        params.height = measuredHeight
    }
}
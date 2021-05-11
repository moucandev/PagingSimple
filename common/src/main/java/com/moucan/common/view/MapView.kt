package com.moucan.common.view

import android.content.Context
import androidx.annotation.Keep
import android.content.res.TypedArray
import android.graphics.*
import com.moucan.common.R
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View

class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //Y轴方向旋转角度
    private var degreeY = 0f

    //不变的那一半，Y轴方向旋转角度
    private var fixDegreeY = 0f

    //Z轴方向（平面内）旋转的角度
    private var degreeZ = 0f
    private val paint: Paint
    private var bitmap: Bitmap? = null
    private val camera: Camera

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bitmapWidth = bitmap!!.width
        val bitmapHeight = bitmap!!.height
        val centerX = width / 2
        val centerY = height / 2
        val x = centerX - bitmapWidth / 2
        val y = centerY - bitmapHeight / 2

        //画变换的一半
        //先旋转，再裁切，再使用camera执行3D动效,**然后保存camera效果**,最后再旋转回来
        canvas.save()
        camera.save()
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        canvas.rotate(-degreeZ)
        camera.rotateY(degreeY)
        camera.applyToCanvas(canvas)
        //计算裁切参数时请注意，此时的canvas的坐标系已经移动
        canvas.clipRect(0, -centerY, centerX, centerY)
        canvas.rotate(degreeZ)
        canvas.translate(-centerX.toFloat(), -centerY.toFloat())
        camera.restore()
        canvas.drawBitmap(bitmap!!, x.toFloat(), y.toFloat(), paint)
        canvas.restore()

        //画不变换的另一半
        canvas.save()
        camera.save()
        canvas.translate(centerX.toFloat(), centerY.toFloat())
        canvas.rotate(-degreeZ)
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, -centerY, 0, centerY)
        //此时的canvas的坐标系已经旋转，所以这里是rotateY
        camera.rotateY(fixDegreeY)
        camera.applyToCanvas(canvas)
        canvas.rotate(degreeZ)
        canvas.translate(-centerX.toFloat(), -centerY.toFloat())
        camera.restore()
        canvas.drawBitmap(bitmap!!, x.toFloat(), y.toFloat(), paint)
        canvas.restore()
    }

    /**
     * 启动动画之前调用，把参数reset到初始状态
     */
    fun reset() {
        degreeY = 0f
        fixDegreeY = 0f
        degreeZ = 0f
    }

    @Keep
    fun setFixDegreeY(fixDegreeY: Float) {
        this.fixDegreeY = fixDegreeY
        invalidate()
    }

    @Keep
    fun setDegreeY(degreeY: Float) {
        this.degreeY = degreeY
        invalidate()
    }

    @Keep
    fun setDegreeZ(degreeZ: Float) {
        this.degreeZ = degreeZ
        invalidate()
    }

    fun setBitmap(bitmap: Bitmap?) {
        this.bitmap = bitmap
        invalidate()
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MapView)
        val drawable = a.getDrawable(R.styleable.MapView_mv_background) as BitmapDrawable?
        a.recycle()
        bitmap = if (drawable != null) {
            drawable.bitmap
        } else {
            BitmapFactory.decodeResource(resources, R.mipmap.flip_board)
        }
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        camera = Camera()
        val displayMetrics = resources.displayMetrics
        val newZ = -displayMetrics.density * 6
        camera.setLocation(0f, 0f, newZ)
    }
}
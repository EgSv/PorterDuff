package com.example.porterduff

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DrawView(this))
    }

    internal inner class DrawView(context: Context): View(context) {
        private var paintSrc: Paint
        private var paintDst: Paint
        private var paintBorder: Paint

        private var pathSrc: Path
        private var pathDst: Path

        private var bitmapSrc: Bitmap
        private var bitmapDst: Bitmap

        private var mode: PorterDuff.Mode = PorterDuff.Mode.DST_OUT

        private var colorDst = Color.argb(170, 0, 0, 255)
        private var colorSrc = Color.argb(85, 255, 255, 0)

        init {
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                setLayerType(LAYER_TYPE_SOFTWARE, null)
            }

            pathDst = Path()
            pathDst.moveTo(0f,0f)
            pathDst.lineTo(500f, 0f)
            pathDst.lineTo(500f, 500f)
            pathDst.close()

            bitmapDst = createBitmap(pathDst, colorDst)

            paintDst = Paint()

            pathSrc = Path()
            pathSrc.moveTo(0f, 0f)
            pathSrc.lineTo(500f, 0f)
            pathSrc.lineTo(0f, 500f)
            pathSrc.close()

            bitmapSrc = createBitmap(pathSrc, colorSrc)

            paintSrc = Paint()
            paintSrc.xfermode = PorterDuffXfermode(mode)

            paintBorder = Paint()
            paintBorder.style = Paint.Style.STROKE
            paintBorder.strokeWidth = 3f
            paintBorder.color = Color.BLACK
        }

        private fun createBitmap(path: Path, color: Int): Bitmap {
            val bitmap: Bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            val bitmapCanvas = Canvas(bitmap)

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = color

            bitmapCanvas.drawPath(path, paint)

            return bitmap
        }

        override fun onDraw(canvas: Canvas?) {
            canvas?.translate(390f, 80f)

            canvas?.drawBitmap(bitmapDst, 0f, 0f, paintDst)

            canvas?.drawBitmap(bitmapSrc, 0f, 0f, paintSrc)

            canvas?.drawRect(0f, 0f, 500f, 500f, paintBorder)
        }
    }
}
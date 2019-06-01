package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import io.github.kenneycode.glkit.GLThread
import io.github.kenneycode.glkit.GLUtils

class SampleGLThreadShareContext : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_common)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)


        var texture = 0
        val bitmap = BitmapFactory.decodeStream(assets.open("test_image.png"))

        val glThread0 = GLThread()
        val glThread1 = GLThread()

        glThread0.init()
        glThread1.init(shareContext = glThread0.getEGLContext())

        glThread0.execute(Runnable {
            texture = GLUtils.bitmap2Texture(bitmap)
        })

        glThread1.execute(Runnable {
            val bitmap = GLUtils.texture2Bitmap(texture, bitmap.width, bitmap.height)
        })

    }

}

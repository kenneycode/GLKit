package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import io.github.kenneycode.glkit.EGL
import io.github.kenneycode.glkit.GLUtils

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class SampleEGLShareContext : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_common)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)

        val egl0 = EGL()
        val egl1 = EGL()

        var texture = 0
        val bitmap = BitmapFactory.decodeStream(assets.open("test_image.png"))

        val thread0 = Thread {

            egl0.init()
            egl1.init(shareContext = egl0.eglContext)

            egl0.bind()
            texture = GLUtils.bitmap2Texture(bitmap)

        }
        thread0.start()

        val thread1 = Thread {
            thread0.join()
            egl1.bind()
            val bitmap = GLUtils.texture2Bitmap(texture, bitmap.width, bitmap.height)
        }
        thread1.start()

    }

}

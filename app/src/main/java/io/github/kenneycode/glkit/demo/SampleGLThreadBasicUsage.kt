package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.opengl.GLES30
import android.os.Bundle
import io.github.kenneycode.glkit.GLThread
import io.github.kenneycode.glkit.GLUtils

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class SampleGLThreadBasicUsage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_common)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)

        val glThread = GLThread()
        glThread.init()

        glThread.post(Runnable {
            val texture = GLUtils.createTexture()
            assert(GLES30.glIsTexture(texture))
            GLUtils.deleteTexture(texture)
        })

    }
}

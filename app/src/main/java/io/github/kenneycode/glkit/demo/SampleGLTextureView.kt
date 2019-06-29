package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.opengl.GLES30
import android.os.Bundle
import io.github.kenneycode.glkit.GLTextureView
import io.github.kenneycode.glkit.GLUtils
import kotlinx.android.synthetic.main.activity_sample_gltextureview.*

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class SampleGLTextureView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_gltextureview)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)

        glTextureView.callback = object : GLTextureView.Callback {

            var testTexture = 0

            override fun onInit() {
                testTexture = GLUtils.createTexture()
                assert(GLES30.glIsTexture(testTexture))
            }

            override fun onRender(width: Int, height: Int) {
                GLES30.glClearColor(0f, 0f, 1f, 1f)
                GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
            }

            override fun onRelease() {
                GLUtils.deleteTexture(testTexture)
            }

        }
        glTextureView.requestRender()

    }

}

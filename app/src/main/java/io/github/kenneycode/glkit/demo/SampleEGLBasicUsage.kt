package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.opengl.GLES30
import android.os.Bundle
import io.github.kenneycode.glkit.EGL
import io.github.kenneycode.glkit.GLUtils

class SampleEGLBasicUsage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_common)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)

        val egl = EGL()
        egl.init()
        egl.bind()

        val texture = GLUtils.createTexture()
        assert(GLES30.glIsTexture(texture))
        GLUtils.deleteTexture(texture)

        egl.unbind()
        egl.release()

    }
}

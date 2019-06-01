package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.graphics.SurfaceTexture
import android.opengl.GLES30
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import io.github.kenneycode.glkit.EGL
import kotlinx.android.synthetic.main.activity_sample_bind_egl_to_textureview.*

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class SampleBindEGLToTextureView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_bind_egl_to_textureview)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)

        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {

            private val egl = EGL()
            private lateinit var surface: Surface

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
            }

            override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture?): Boolean {
                surface.release()
                egl.release()
                return false
            }

            override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture?, width: Int, height: Int) {
                surface = Surface(surfaceTexture)
                egl.init(surface)
                egl.bind()

                GLES30.glClearColor(0f, 0f, 1f, 1f)
                GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
                egl.swapBuffers()

                egl.unbind()
            }

        }

    }

}

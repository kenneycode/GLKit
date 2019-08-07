package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.opengl.GLES30
import android.os.Bundle
import android.view.SurfaceHolder
import io.github.kenneycode.glkit.EGL
import io.github.kenneycode.glkit.GLThread
import kotlinx.android.synthetic.main.activity_sample_bind_egl_to_surfaceview.*

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class SampleBindGLThreadToSurfaceView : Activity() {

    private val egl = EGL()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_bind_egl_to_surfaceview)
        title = intent.getStringExtra(Constants.KEY_SAMPLE_NAME)

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {

            private val glThread = GLThread()

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                glThread.release()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {

                glThread.init(holder!!.surface)

                glThread.post({
                    GLES30.glClearColor(0f, 0f, 1f, 1f)
                    GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
                }, true)

            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        egl.release()
    }
}

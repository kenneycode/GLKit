package io.github.kenneycode.glkit

import android.opengl.EGL14
import android.opengl.EGLContext
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import java.util.concurrent.Semaphore

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class GLThread {

    private val handlerThread : HandlerThread = HandlerThread("GLThread")
    private lateinit var handler : Handler
    lateinit var egl : EGL

    fun init(surface: Surface? = null, shareContext: EGLContext = EGL14.EGL_NO_CONTEXT) {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        egl = EGL()
        egl.init(surface, shareContext)
        handler.post {
            egl.bind()
        }
    }

    fun post(r : Runnable, render: Boolean = false) {
        handler.post {
            r.run()
            if (render) {
                egl.swapBuffers()
            }
        }
    }

    fun execute(r : Runnable, render: Boolean = false) {
        val semaphore = Semaphore(0)
        handler.post {
            r.run()
            if (render) {
                egl.swapBuffers()
            }
            semaphore.release()
        }
        semaphore.acquire()
    }

    fun getEGLContext(): EGLContext {
        return egl.eglContext
    }

    fun release() {
        handlerThread.looper.quit()
        egl.release()
    }

}
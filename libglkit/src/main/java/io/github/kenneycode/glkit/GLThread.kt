package io.github.kenneycode.glkit

import android.opengl.EGL14
import android.opengl.EGLContext
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import java.util.concurrent.Semaphore

/**
 *
 *      带有EGL环境的线程封装
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

    /**
     *
     * 初始化GLThread (init GLThread)
     *
     * @param surface 要绑定的surface (the surface to bind)
     * @param shareContext 要共享的EGL Context (the EGL context to share)
     *
     */
    fun init(surface: Surface? = null, shareContext: EGLContext = EGL14.EGL_NO_CONTEXT) {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        egl = EGL()
        egl.init(surface, shareContext)
        handler.post {
            egl.bind()
        }
    }

    /**
     *
     * 在GLThread异步执行一个任务 (asynchronously run a task in GLThread)
     *
     * @param task 要执行的任务 (the task to run)
     * @param render 这个任务是否需要进行渲染 (indicates if the task needs to render)
     *
     */
    fun post(task: () -> Unit, render: Boolean = false) {
        handler.post {
            task.invoke()
            if (render) {
                egl.swapBuffers()
            }
        }
    }

    /**
     *
     * 在GLThread同步执行一个任务 (synchronously run a task in GLThread)
     *
     * @param task 要执行的任务 (the task to run)
     * @param render 这个任务是否需要进行渲染 (indicates if the task needs to render)
     *
     */
    fun execute(task: () -> Unit, render: Boolean = false) {
        val semaphore = Semaphore(0)
        handler.post {
            task.invoke()
            if (render) {
                egl.swapBuffers()
            }
            semaphore.release()
        }
        semaphore.acquire()
    }

    /**
     *
     * 获取与GLThread绑定的EGL Context (obtain the EGL Context bound to the GLThread)
     *
     * @return 与GLThread绑定的EGL (the EGL Context bound to the GLThread)
     *
     */

    fun getEGLContext(): EGLContext {
        return egl.eglContext
    }

    /**
     *
     * 停止线程和释放资源
     * stop the thread and release resources
     *
     */
    fun release() {
        handlerThread.looper.quit()
        egl.release()
    }

}
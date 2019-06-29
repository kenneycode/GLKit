package io.github.kenneycode.glkit

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.EGLContext
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView

/**
 *
 *      自带GL环境的TextureView
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

open class GLTextureView : TextureView {

    protected var surfaceTextureWidth = 0
    protected var surfaceTextureHeight = 0
    protected var glThread: GLThread? = null

    var callback: Callback? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        surfaceTextureListener = object : SurfaceTextureListener {

            private var surface: Surface? = null

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
            }

            override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture?) {
            }

            override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture?): Boolean {
                return false
            }

            override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                surfaceTextureWidth = width
                surfaceTextureHeight = height
                surface = Surface(surfaceTexture)
                glThread = GLThread()
                glThread?.init(surface)
                glThread?.post(Runnable {
                    callback?.onInit()
                    requestRender()
                })
            }

        }
    }

    /**
     *
     * 请求执行一次渲染，调用后会回调Callback#onRender (Request a render operation, which will call Callback#onRender)
     *
     */
    fun requestRender() {
        glThread?.post(Runnable {
            callback?.onRender(surfaceTextureWidth, surfaceTextureHeight)
        }, true)
    }

    /**
     *
     * 在GL线程同步执行一个任务 (Execute a task synchronously on GL thread)
     *
     */
    fun executeOnGLThread(r : Runnable) {
        glThread?.execute(r)
    }

    /**
     *
     * 在GL线程异步执行一个任务 (Execute a task asynchronously on GL thread)
     *
     */
    fun postToGLThread(r : Runnable) {
        glThread?.post(r)
    }

    /**
     *
     * 获取EGLContext (obtain EGLContext)
     *
     */
    fun getEGLContext(): EGLContext? {
        return glThread?.getEGLContext()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        glThread?.execute(Runnable {
            callback?.onRelease()
        })
        glThread?.release()
    }

    /**
     *
     * 回调方法，均在GL线程回调 (The callbacks which are called on GL thread)
     *
     */
    interface Callback {

        /**
         *
         * 初化始回调 (Init callback)
         *
         */
        fun onInit()

        /**
         *
         * 渲染回调 (Render callback)
         *
         * @param width     surface的宽度 (The width of surface)
         * @param height    surface的高度 (The height of surface)
         *
         */
        fun onRender(width: Int, height: Int)

        /**
         *
         * 释放回调 (Release callback)
         *
         */
        fun onRelease()
    }

}
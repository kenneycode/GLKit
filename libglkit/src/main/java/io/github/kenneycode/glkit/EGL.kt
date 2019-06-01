package io.github.kenneycode.glkit

import android.opengl.EGL14
import android.opengl.EGLContext
import android.opengl.EGLExt
import android.util.Log
import android.view.Surface

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class EGL {

    private var eglDisplay = EGL14.EGL_NO_DISPLAY
    private var eglSurface = EGL14.EGL_NO_SURFACE
    var eglContext = EGL14.EGL_NO_CONTEXT

    private var previousDisplay = EGL14.EGL_NO_DISPLAY
    private var previousDrawSurface = EGL14.EGL_NO_SURFACE
    private var previousReadSurface = EGL14.EGL_NO_SURFACE
    private var previousContext = EGL14.EGL_NO_CONTEXT

    fun init(surface: Surface? = null, shareContext: EGLContext? = EGL14.EGL_NO_CONTEXT) {
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        val version = IntArray(2)
        EGL14.eglInitialize(eglDisplay, version, 0, version, 1)
        val attribList = intArrayOf(
            EGL14.EGL_RED_SIZE, 8, EGL14.EGL_GREEN_SIZE, 8, EGL14.EGL_BLUE_SIZE, 8, EGL14.EGL_ALPHA_SIZE, 8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT or EGLExt.EGL_OPENGL_ES3_BIT_KHR, EGL14.EGL_NONE, 0,
            EGL14.EGL_NONE
        )
        val eglConfig = arrayOfNulls<android.opengl.EGLConfig>(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(
                eglDisplay, attribList, 0, eglConfig, 0, eglConfig.size,
                numConfigs, 0
            )
        eglContext = EGL14.eglCreateContext(
            eglDisplay, eglConfig[0], shareContext,
            intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 3, EGL14.EGL_NONE), 0
        )
        val surfaceAttribs = intArrayOf(EGL14.EGL_NONE)
        eglSurface = if (surface == null) {
            EGL14.eglCreatePbufferSurface(eglDisplay, eglConfig[0], surfaceAttribs, 0)
        } else {
            EGL14.eglCreateWindowSurface(eglDisplay, eglConfig[0], surface, surfaceAttribs, 0)
        }
    }

    fun bind() {
        previousDisplay = EGL14.eglGetCurrentDisplay()
        previousDrawSurface = EGL14.eglGetCurrentSurface(EGL14.EGL_DRAW)
        previousReadSurface = EGL14.eglGetCurrentSurface(EGL14.EGL_READ )
        previousContext = EGL14.eglGetCurrentContext()
        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)
    }

    fun swapBuffers(): Boolean {
        return EGL14.eglSwapBuffers(eglDisplay, eglSurface)
    }

    fun unbind() {
        EGL14.eglMakeCurrent(previousDisplay, previousDrawSurface, previousReadSurface, previousContext)
    }

    fun release() {
        if (eglDisplay !== EGL14.EGL_NO_DISPLAY) {
            EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)
            EGL14.eglDestroySurface(eglDisplay, eglSurface)
            EGL14.eglDestroyContext(eglDisplay, eglContext)
            EGL14.eglReleaseThread()
            EGL14.eglTerminate(eglDisplay)
        }
        eglDisplay = EGL14.EGL_NO_DISPLAY
        eglContext = EGL14.EGL_NO_CONTEXT
        eglSurface = EGL14.EGL_NO_SURFACE

    }

    private fun checkEglError(msg: String) {
        val error= EGL14.eglGetError()
        if (error != EGL14.EGL_SUCCESS) {
            throw RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error))
        }
    }

}
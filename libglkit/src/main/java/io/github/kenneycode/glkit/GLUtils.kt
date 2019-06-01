package io.github.kenneycode.glkit

import android.graphics.Bitmap
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLES30
import android.util.Log
import java.nio.ByteBuffer

/**
 * Created by kenneyqin on 2019/4/11
 */

class GLUtils {

    companion object {

        fun oesTexture2Bitmap(texture : Int, width : Int, height : Int) : Bitmap {
            val buffer = ByteBuffer.allocate(width * height * 4)
            val frameBuffers = IntArray(1)
            checkGLError()
            GLES30.glGenFramebuffers(frameBuffers.size, frameBuffers, 0)
            checkGLError()
            GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture)
            checkGLError()
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBuffers[0])
            checkGLError()
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture, 0)
            GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            checkGLError()
            buffer.position(0)
            bitmap.copyPixelsFromBuffer(buffer)
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0, 0)
            checkGLError()
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0)
            GLES30.glDeleteFramebuffers(frameBuffers.size, frameBuffers, 0)
            checkGLError()
            return bitmap
        }

        fun texture2Bitmap(texture : Int, width : Int, height : Int) : Bitmap {
            val buffer = ByteBuffer.allocate(width * height * 4)
            val frameBuffers = IntArray(1)
            checkGLError()
            GLES30.glGenFramebuffers(frameBuffers.size, frameBuffers, 0)
            checkGLError()
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture)
            checkGLError()
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBuffers[0])
            checkGLError()
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, texture, 0)
            GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            checkGLError()
            buffer.position(0)
            bitmap.copyPixelsFromBuffer(buffer)
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, 0, 0)
            checkGLError()
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0)
            GLES30.glDeleteFramebuffers(frameBuffers.size, frameBuffers, 0)
            checkGLError()
            return bitmap
        }

        fun createTexture() : Int {
            val textures = IntArray(1)
            GLES30.glGenTextures(textures.size, textures, 0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0])
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
            return textures[0]
        }

        fun createOESTexture() : Int {
            val textures = IntArray(1)
            GLES30.glGenTextures(textures.size, textures, 0)
            GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0])
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST)
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
            return return textures[0]
        }

        fun deleteTexture(texture : Int) {
            val textures = intArrayOf(texture)
            GLES30.glDeleteTextures(1, textures, 0)
        }

        fun deleteFrameBuffer(frameBuffer : Int) {
            val frameBuffers = intArrayOf(frameBuffer)
            GLES30.glDeleteTextures(1, frameBuffers, 0)
        }

        fun bitmap2Texture(bitmap : Bitmap) : Int {
            val texture = createTexture()
            loadBitmap2Texture(bitmap, texture)
            return texture
        }

        fun loadBitmap2Texture(bitmap : Bitmap, texture : Int) {
            val buffer = ByteBuffer.allocateDirect(bitmap.width * bitmap.height * 4)
            bitmap.copyPixelsToBuffer(buffer)
            buffer.position(0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture)
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, bitmap.width, bitmap.height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer)
        }

        fun checkGLError() {
            val error = GLES30.glGetError()
            if (error != GLES30.GL_NO_ERROR) {
                val hexErrorCode = Integer.toHexString(error)
                Log.e("debug", "glError: $hexErrorCode")
                throw RuntimeException("GLError")
            }
        }

    }

}
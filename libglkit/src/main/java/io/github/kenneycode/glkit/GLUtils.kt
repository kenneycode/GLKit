package io.github.kenneycode.glkit

import android.graphics.Bitmap
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLES30
import android.util.Log
import java.nio.ByteBuffer

/**
 *
 *      GL常用操作Util类
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 *
 **/

class GLUtils {

    companion object {
        
        private val TAG = "GLUtils"

        /**
         *
         * 将一个OES纹理转换成bitmap (convert a OES texture to bitmap)
         *
         * @param texture 要转换的OES纹理 (the OES texture to convert)
         * @param width 纹理宽 (the width of the texture)
         * @param height 纹理高 (the height of the texture)
         *
         * @return 转换后的bitmap (the converted bitmap)
         *
         */
        fun oesTexture2Bitmap(texture: Int, width: Int, height: Int): Bitmap {
            val buffer = ByteBuffer.allocate(width * height * 4)
            val frameBuffers = IntArray(1)
            GLES30.glGenFramebuffers(frameBuffers.size, frameBuffers, 0)
            GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBuffers[0])
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture, 0)
            GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            buffer.position(0)
            bitmap.copyPixelsFromBuffer(buffer)
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0, 0)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0)
            GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)
            GLES30.glDeleteFramebuffers(frameBuffers.size, frameBuffers, 0)
            return bitmap
        }

        /**
         *
         * 将一个纹理转换成bitmap (convert a texture to bitmap)
         *
         * @param texture 要转换的纹理 (the texture to convert)
         * @param width 纹理宽 (the width of the texture)
         * @param height 纹理高 (the height of the texture)
         *
         * @return 转换后的bitmap (the converted bitmap)
         *
         */
        fun texture2Bitmap(texture: Int, width: Int, height: Int): Bitmap {
            val buffer = ByteBuffer.allocate(width * height * 4)
            val frameBuffers = IntArray(1)
            GLES30.glGenFramebuffers(frameBuffers.size, frameBuffers, 0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frameBuffers[0])
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, texture, 0)
            GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            buffer.position(0)
            bitmap.copyPixelsFromBuffer(buffer)
            GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, 0, 0)
            GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
            GLES30.glDeleteFramebuffers(frameBuffers.size, frameBuffers, 0)
            return bitmap
        }

        /**
         *
         * 创建一个纹理 (create a texture)
         *
         * @return 创建好的纹理 (the created texture)
         *
         */
        fun createTexture(): Int {
            val textures = IntArray(1)
            GLES30.glGenTextures(textures.size, textures, 0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0])
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
            return textures[0]
        }

        /**
         *
         * 创建一个OES纹理 (create a OES texture)
         *
         * @return 创建好的纹理 (the created texture)
         *
         */
        fun createOESTexture(): Int {
            val textures = IntArray(1)
            GLES30.glGenTextures(textures.size, textures, 0)
            GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0])
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE)
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR)
            GLES30.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_EXTERNAL_OES, 0)
            return textures[0]
        }

        /**
         *
         * 删除一个纹理 (delete a texture)
         *
         * @param texture 要删除的纹理 (the texture to delete)
         *
         */
        fun deleteTexture(texture: Int) {
            val textures = intArrayOf(texture)
            GLES30.glDeleteTextures(1, textures, 0)
        }

        /**
         *
         * 删除一个frame buffer (delete a frame buffer)
         *
         * @param frameBuffer 要删除的frame buffer (the frame buffer to delete)
         *
         */
        fun deleteFrameBuffer(frameBuffer: Int) {
            val frameBuffers = intArrayOf(frameBuffer)
            GLES30.glDeleteTextures(1, frameBuffers, 0)
        }

        /**
         *
         * 将bitmap转换成纹理 (convert bitmap to texture)
         *
         * @return 转换好的纹理 (the converted texture)
         *
         */
        fun bitmap2Texture(bitmap: Bitmap): Int {
            val texture = createTexture()
            loadBitmap2Texture(bitmap, texture)
            return texture
        }

        /**
         *
         * 将bitmap加载到指定的纹理中 (load a bitmap into the specified texture)
         *
         * @param bitmap 要加载的bitmap (the bitmap)
         * @param texture 要加载到的纹理 (the texture to store the bitmap data)
         *
         */
        fun loadBitmap2Texture(bitmap: Bitmap, texture: Int) {
            val buffer = ByteBuffer.allocateDirect(bitmap.width * bitmap.height * 4)
            bitmap.copyPixelsToBuffer(buffer)
            buffer.position(0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture)
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, bitmap.width, bitmap.height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer)
        }

        /**
         *
         * 检查GL错误 (check GL Error)
         *
         */
        fun checkGLError() {
            val error = GLES30.glGetError()
            if (error != GLES30.GL_NO_ERROR) {
                val hexErrorCode = Integer.toHexString(error)
                Log.e(TAG, "glError: 0x$hexErrorCode")
                throw RuntimeException("GLError")
            }
        }

    }

}
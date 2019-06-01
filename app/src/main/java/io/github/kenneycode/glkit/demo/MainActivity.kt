package io.github.kenneycode.glkit.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 *      Coded by kenney
 *
 *      http://www.github.com/kenneycode
 *
 **/

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleItems = arrayOf(
            SampleItem(getString(R.string.sample_egl_basic_usage), SampleEGLBasicUsage::class.java),
            SampleItem(getString(R.string.sample_bind_egl_to_surfaceview), SampleBindEGLToSurfaceView::class.java),
            SampleItem(getString(R.string.sample_bind_egl_to_textureview), SampleBindEGLToTextureView::class.java),
            SampleItem(getString(R.string.sample_egl_share_context), SampleEGLShareContext::class.java),
            SampleItem(getString(R.string.sample_glthread_basic_usage), SampleGLThreadBasicUsage::class.java),
            SampleItem(getString(R.string.sample_bind_glthread_to_surfaceview), SampleBindEGLToSurfaceView::class.java),
            SampleItem(getString(R.string.sample_bind_glthread_to_textureview), SampleBindEGLToTextureView::class.java),
            SampleItem(getString(R.string.sample_glthread_share_context), SampleGLThreadShareContext::class.java)
        )

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        samples.layoutManager = layoutManager
        samples.adapter = SampleAdapter(sampleItems)

    }

    inner class SampleItem(val sampleSame: String, val sampleActivity: Class<*>)

    inner class SampleAdapter(private val sampleItems: Array<SampleItem>) : RecyclerView.Adapter<VH>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
            return VH(LayoutInflater.from(p0.context).inflate(R.layout.item_sample, null, false))
        }

        override fun getItemCount(): Int {
            return sampleItems.size
        }

        override fun onBindViewHolder(vh: VH, position: Int) {
            vh.button.text = sampleItems[position].sampleSame
            vh.button.setOnClickListener {
                val intent = Intent(this@MainActivity, sampleItems[position].sampleActivity)
                intent.putExtra(Constants.KEY_SAMPLE_NAME, sampleItems[position].sampleSame)
                this@MainActivity.startActivity(intent)
            }
        }

    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button = itemView.findViewById<Button>(R.id.button)
    }

}
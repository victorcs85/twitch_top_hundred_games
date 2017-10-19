package br.com.pan.desafio.victorcs.top100games.ui.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import br.com.pan.desafio.victorcs.top100games.R
import br.com.pan.desafio.victorcs.top100games.logic.presenter.DetailPresenter
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_counter_card.*


/**
 * Created by Victor Santiago on 16/10/2017.
 */
class DetailActivity : BaseActivity(), DetailPresenter.IDetailView {

    private var mPresenter: DetailPresenter? = null

    //region Activity life cycle
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
        DetailPresenter(this, intent)

    }

    override fun onBackPressed() {

        finish()
        overridePendingTransition(R.anim.slide_out_return, R.anim.slide_in_return)

    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

        if (menuItem.itemId == android.R.id.home) {

            onBackPressed()

        }
        return super.onOptionsItemSelected(menuItem)

    }

    override fun onRestart() {
        super.onRestart()
        isCalledActivity = false
    }

    //endregion

    //region IDetailView
    override fun setupToolbar(image: String, title: String) {

        ctl.title = title
        setSupportActionBar(toolbar)

        Glide.
                with(this).
                load(image).
                asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).
                centerCrop().
                dontAnimate().
                override(1080, 675).
                into(object : BitmapImageViewTarget(ivToolbarGame) {
                })

        val drawable = toolbar.navigationIcon
        drawable?.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun setPresenter(presenter: DetailPresenter) {

        this.mPresenter = presenter
        this.mPresenter!!.start()

    }

    override fun setupChannelCounter(value: Int) {

        tvChannelCounter.text = resources.getQuantityString(R.plurals.channels, value, value)

    }

    override fun setupNumberOfViews(value: Int) {

        tvCountViews.text = resources.getQuantityString(R.plurals.views, value, value)

    }

    companion object {

        private var isCalledActivity = false
    }
    //endregion
}

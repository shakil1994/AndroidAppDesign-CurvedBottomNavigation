package com.example.shakil.kotlincbn

import android.animation.ValueAnimator
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.sdsmdg.harjot.vectormaster.VectorMasterView
import com.sdsmdg.harjot.vectormaster.models.PathModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_cart -> {
                //Animation
                draw(6)

                //Find the correct path using name
                lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                fab.visibility = View.VISIBLE
                fab1.visibility = View.GONE
                fab2.visibility = View.GONE
                drawAnimation(fab)

                //Event
                Toast.makeText(this, "Click to Cart", Toast.LENGTH_SHORT).show()
            }

            R.id.action_shipping -> {
                //Animation
                draw(2)

                //Find the correct path using name
                lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                fab.visibility = View.GONE
                fab1.visibility = View.VISIBLE
                fab2.visibility = View.GONE
                drawAnimation(fab1)

                //Event
                Toast.makeText(this, "Click to Shipping", Toast.LENGTH_SHORT).show()
            }

            R.id.action_customer -> {
                //Animation
                draw()

                //Find the correct path using name
                lin_id.x = bottom_nav.mFirstCurveControlPoint1.x.toFloat()
                fab.visibility = View.GONE
                fab1.visibility = View.GONE
                fab2.visibility = View.VISIBLE
                drawAnimation(fab2)

                //Event
                Toast.makeText(this, "Click to Customer", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun draw() {
        bottom_nav.mFirstCurveStartPoint.set(
            bottom_nav.mNavigationBarWidth * 10 / 12 - bottom_nav.CURVE_CIRCLE_RADIUS * 2 - bottom_nav.CURVE_CIRCLE_RADIUS / 3,
            0
        )

        bottom_nav.mFirstCurveEndPoint.set(
            bottom_nav.mNavigationBarWidth * 10 / 12,
            bottom_nav.CURVE_CIRCLE_RADIUS + bottom_nav.CURVE_CIRCLE_RADIUS / 4
        )

        bottom_nav.mSecondCurveEndPoint.set(
            bottom_nav.mNavigationBarWidth * 10 / 12 + bottom_nav.CURVE_CIRCLE_RADIUS * 2 +
                    bottom_nav.CURVE_CIRCLE_RADIUS / 3, 0
        )

        bottom_nav.mFirstCurveControlPoint1.set(
            bottom_nav.mFirstCurveStartPoint.x + bottom_nav.CURVE_CIRCLE_RADIUS +
                    bottom_nav.CURVE_CIRCLE_RADIUS / 4, bottom_nav.mFirstCurveStartPoint.y
        )

        bottom_nav.mFirstCurveControlPoint2.set(
            bottom_nav.mFirstCurveEndPoint.x - bottom_nav.CURVE_CIRCLE_RADIUS * 2 + bottom_nav.CURVE_CIRCLE_RADIUS,
            bottom_nav.mFirstCurveEndPoint.y
        )

        //Same with second
        bottom_nav.mSecondCurveControlPoint1.set(
            bottom_nav.mSecondCurveStartPoint.x + bottom_nav.CURVE_CIRCLE_RADIUS * 2 - bottom_nav.CURVE_CIRCLE_RADIUS,
            bottom_nav.mSecondCurveStartPoint.y
        )

        bottom_nav.mSecondCurveControlPoint2.set(
            bottom_nav.mSecondCurveEndPoint.x - bottom_nav.CURVE_CIRCLE_RADIUS + bottom_nav.CURVE_CIRCLE_RADIUS / 4,
            bottom_nav.mSecondCurveEndPoint.y
        )
    }

    private fun drawAnimation(fab: VectorMasterView?) {
        outline = fab!!.getPathModelByName("outline")
        outline.strokeColor = Color.WHITE
        outline.trimPathEnd = 0.0f

        //Init valueAnimator
        val valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        valueAnimator.duration = 1000

        valueAnimator.addUpdateListener {
            outline.trimPathEnd = valueAnimator.animatedValue as Float
            fab.update()
        }
        valueAnimator.start()
    }

    private fun draw(i: Int) {
        bottom_nav.mFirstCurveStartPoint.set(
            bottom_nav.mNavigationBarWidth / i
                    - bottom_nav.CURVE_CIRCLE_RADIUS * 2 -
                    bottom_nav.CURVE_CIRCLE_RADIUS / 3, 0
        )

        //The coordinate of endpoint after curve
        bottom_nav.mFirstCurveEndPoint.set(
            bottom_nav.mNavigationBarWidth / i,
            (bottom_nav.CURVE_CIRCLE_RADIUS +
                    (bottom_nav.CURVE_CIRCLE_RADIUS / 4))
        )

        //Same thing for second
        bottom_nav.mSecondCurveStartPoint = bottom_nav.mFirstCurveEndPoint

        bottom_nav.mSecondCurveEndPoint.set(
            bottom_nav.mNavigationBarWidth / i
                    + bottom_nav.CURVE_CIRCLE_RADIUS * 2 +
                    bottom_nav.CURVE_CIRCLE_RADIUS / 3, 0
        )

        //The coordinate of first control point on a cubic curve
        bottom_nav.mFirstCurveControlPoint1.set(
            bottom_nav.mFirstCurveStartPoint.x
                    + bottom_nav.CURVE_CIRCLE_RADIUS +
                    bottom_nav.CURVE_CIRCLE_RADIUS / 4,
            bottom_nav.mFirstCurveStartPoint.y
        )

        //The coordinate of second control point on a cubic curve
        bottom_nav.mFirstCurveControlPoint2.set(
            bottom_nav.mFirstCurveEndPoint.x
                    - bottom_nav.CURVE_CIRCLE_RADIUS * 2
                    + bottom_nav.CURVE_CIRCLE_RADIUS,
            bottom_nav.mFirstCurveEndPoint.y
        )

        //Same with second
        bottom_nav.mSecondCurveControlPoint1.set(
            bottom_nav.mSecondCurveStartPoint.x
                    + bottom_nav.CURVE_CIRCLE_RADIUS * 2 -
                    bottom_nav.CURVE_CIRCLE_RADIUS,
            bottom_nav.mSecondCurveStartPoint.y
        )

        bottom_nav.mSecondCurveControlPoint2.set(
            bottom_nav.mSecondCurveEndPoint.x
                    - bottom_nav.CURVE_CIRCLE_RADIUS +
                    bottom_nav.CURVE_CIRCLE_RADIUS / 4,
            bottom_nav.mSecondCurveEndPoint.y
        )
    }

    internal lateinit var outline: PathModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav.inflateMenu(R.menu.main_menu) //need first, inflate menu

        bottom_nav.selectedItemId = R.id.action_shipping //need second, set item default

        bottom_nav.setOnNavigationItemSelectedListener(this@MainActivity) //need third, add listener

    }
}

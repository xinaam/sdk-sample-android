package com.xfinite.mzaalosdksample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xfinite.mzaalorewards.MzaaloRewards
import com.xfinite.mzaalorewards.MzaaloRewardsActionTypes
import com.xfinite.mzaalorewards.MzaaloRewardsBalanceListener
import com.xfinite.mzaalorewards.MzaaloRewardsRegisterActionListener
import kotlinx.android.synthetic.main.activity_reward.*
import org.json.JSONObject

class RewardActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)

        btnRegister.setOnClickListener {
            progress.visibility = View.VISIBLE

            if (edtUserMetadata.text.toString() != "") {
                //Add to Register Rewards Action
                MzaaloRewards.registerRewardAction(getSelectedRewardActionType(), JSONObject(edtUserMetadata.text.toString()), object :MzaaloRewardsRegisterActionListener{
                    override fun onActionRegistered() {
                        progress.visibility = View.GONE
                        Toast.makeText(this@RewardActivity,"Registration Success",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: String) {
                        progress.visibility = View.GONE
                        Toast.makeText(this@RewardActivity,"Registration failed : $error",Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                MzaaloRewards.registerRewardAction(getSelectedRewardActionType(), JSONObject(), object :MzaaloRewardsRegisterActionListener{
                    override fun onActionRegistered() {
                        progress.visibility = View.GONE
                        Toast.makeText(this@RewardActivity,"Registration Success",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: String) {
                        progress.visibility = View.GONE
                        Toast.makeText(this@RewardActivity,"Registration failed : $error",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        btnFetchBalance.setOnClickListener {
            progress.visibility = View.VISIBLE
            txtRewardBalance.visibility = View.GONE

            //Add to Fetch Reward Balance
            MzaaloRewards.getBalance(object :MzaaloRewardsBalanceListener{
                override fun onBalanceFetched(balance: Int?) {
                    progress.visibility = View.GONE
                    txtRewardBalance.visibility = View.VISIBLE
                    txtRewardBalance.setText("Balance : $balance")
                }

                override fun onError(error: String) {
                    progress.visibility = View.GONE
                    Toast.makeText(this@RewardActivity,"Fetch failed : $error",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun getSelectedRewardActionType(): MzaaloRewardsActionTypes {
        return when {
            spinnerRewardActionType.selectedItem.toString() == "CONTENT_VIEWED" -> {
                MzaaloRewardsActionTypes.CONTENT_VIEWED
            }
            spinnerRewardActionType.selectedItem.toString() == "CHECKED_IN" -> {
                MzaaloRewardsActionTypes.CHECKED_IN
            }
            spinnerRewardActionType.selectedItem.toString() == "SIGNED_UP" -> {
                MzaaloRewardsActionTypes.SIGNED_UP
            }
            else -> {
                MzaaloRewardsActionTypes.REFERRAL_APPLIED
            }
        }
    }
}




# Mzaalo Android SDK
This is the official documentation for the integration of Mzaalo android SDKs in any android application with a valid partner code.

## Table of contents

 - [Overview](#overview)
 - [Installation](#installation)
	 - [Requirements](#requirements)
	 - [Configuration](#configuration)
- [Getting Started](#getting-started)
	- [Init](#init)
	- [IsInitSuccessful](#isInitSuccessful)
- [Features and Implementation](#features-and-implementation)
	- [auth](#auth)
		- [Login](#login)
		- [Logout](#logout)
		- [Get User](#get-user)
	- [rewards](#rewards)
		- [Register Rewards Action](#register-rewards-action)
		- [Fetch Reward Balance](#fetch-reward-balance)
	- [player](#player)
		- [Initialization](#initialization)
		- [Playback Controls](#playback-controls)
		- [Cleanup](#cleanup)
- [Sequence flow](#sequence-flow)
- [References](#references)
	- [Error Codes](#error-codes)

## Overview
Mzaalo SDKs have three modules:

 1. **mzaalo-auth** : This module contains authentication features like login, logout, etc.
 2. **mzaalo-rewards** : This module contains all the authentication features, plus features of rewards like adding rewards, fetching balance, etc.
 3. **mzaalo-player**: This module contains all the rewards and auth features, plus a video player inbuild video with a high level implementation of playback controls

All these modules are shippable as separate gradle/maven dependencies.
Structurally, `mzaalo-auth` is the subset of `mzaalo-rewards` and `mzaalo-rewards` is the subset of `mzaalo-player`. This means, any application that includes the dependency for `mzaalo-rewards` automatically gets the functionality for `mzaalo-auth` out of the box. Also, any application that includes the dependency of `mzaalo-player` automatically gets the functionality for `mzaalo-auth` and `mzaalo-rewards` out of the box.

Following is the pictorial representation of the dependencies and their subsets:
![Dependency of modules](https://xfinitesite.blob.core.windows.net/flow-diagrams/venn-sdk-mobile.png)

    
## Installation

### Requirements

 - Android 4.1 (API Level 16) and above
 - [AndroidX](https://developer.android.com/jetpack/androidx/)

### Configuration
Add `mzaalo-player` or `mzaalo-rewards` or `mzaalo-auth` to the application level `build.gradle` file:

    dependencies{
	    ...

	    implementation 'com.mzaalo:mzaalo-xxxx:2.0.8'
	    ...
    }

where, **`xxxx`** can be `auth` or `rewards` or `player`

Add Mzaalo's Maven url repository in `allprojects` block in your project level `build.gradle` file:

    allprojects {
    ...
      repositories {
      ...
        maven {
          url "https://dl.bintray.com/xfiniteio/MzaaloSDKs"
        }
      }
    }



## Getting Started

### init

The entry point to the SDK is through the `init` function that gets called with a valid partner code, a callback based Mzaalo interface object, and an identifier for the environment type(STAGING or PRODUCTION). Call this function in the `onCreate` function of your Application class.

    MzaaloPlayer.init(context, "YOUR_PARTNER_CODE", initListener, MzaaloEnvironment.XXXX)

Here `initListener` can be either a `MzaaloAuthInitListener` or `MzaaloRewardsInitListener` or `MzaaloPlayerInitListener`(depending upon the dependency included) object with the following definition.

    interface MzaaloPlayerInitListener{
	    fun onSuccess()
	    fun onError(error:MzError)
    }



`MzaaloEnvironment` is an enum class with the following options:

 - **MzaaloEnvironment.STAGING**
 - **MzaaloEnvironment.PRODUCTION**

Expected values of `code` field in `MzError` object are:

 - 3001
 - 3008



### isInitSuccessful

A utility function to check if the Mzaalo SDK is properly initialised or not. This function can be called on either `MzaaloPlayer` or `MzaaloRewards` or `MzaaloAuth` (depending upon the dependency included).

    MzaaloPlayer.isInitSuccessful()
    
returns `Boolean` value


## Features and Implementation
### auth
#### Login
Your application should call the `MzaaloAuth.login()` function as soon as the user is identified at your end.

	

    val userMeta=JSONObject()
    userMeta.put(userProperty, value)
    MzaaloAuth.login("UNIQUE_ID_OF_YOUR_USER", userMeta, loginListener)

Here are the valid `userProperty` fields that can put as keys in the `userMeta` json. A field can be mandatory or optional depending upon the partner configuration in the system. 
|userProperty|Description|Data type|Example|
|--|--|--|--|
|email|Email Address of the user|String|johndoe@example.com|
|phone|Phone number of the user|String|9876543210|
|country_code|Country code of the user's phone number|String|+91, +44|


Here `loginListener` is the object of interface `MzaaloAuthLoginListener` that has the following definition:

    interface MzaaloAuthLoginListener{
	    fun onLoginSuccess(user:User)
	    fun onError(error:MzError)
    }


Expected values of `code` field in `MzError` object are:

 - 3008
 - 3011
 - 3012
 - 3013
 - 3014
 - 3015
 - 3016
 - 3041

#### Logout
Your application should call `MzaaloAuth.logout()` function when the user logs out from your application or when the user identitiy is no longer available to you.

    MzaaloAuth.logout()

#### Get User
Call this function if you want to fetch the currently logged in user object.

    MzaaloAuth.getUser()

returns  `null` if not-logged in, and `User` object otherwise.



### rewards
#### Register Rewards Action
This is a feature that allows the application to register an action to the Mzaalo SDK, that should credit some rewards to the user.

    val eventMeta=JSONObject()
    eventMeta(eventProperty, value)
    MzaaloRewards.registerRewardAction(MzaaloRewardsActionTypes.XXXX, eventMeta, actionListener)


`MzaaloRewardsActionTypes` is an enum class that describes the type of action that the user has performed. The enum has following options:
| Enum Value | Description |
|--|--|
| `MzaaloRewardsActionTypes.CONTENT_VIEWED` | Send this if you want to give rewards to the user for watching content |
| `MzaaloRewardsActionTypes.CHECKED_IN` | Send this if you want to give rewards to the user for **launching the app** or **visiting some section of the app** on a daily basis |
| `MzaaloRewardsActionTypes.SIGNED_UP` | Send this if you want to give reward to the user for signing up on your application. In this case, call this once the above mentioned login function has been successfully executed. |
| `MzaaloRewardsActionTypes.REFERRAL_APPLIED` | When a user applies a referral code on the platform which he/she received from some other user previously. This will credit the rewards to the user who referred the current user. |



Here are the valid `eventProperty` fields that can be put as keys in the `eventMeta` json:
| eventProperty | MzaaloRewardsActionTypes | Description | Data type | Example |
|--|--|--|--|--|
| total_watch_time | `CONTENT_VIEWED` | The duration(in seconds) for which the user has watched the content | Integer | 600, (if the user watched a movie for ten minutes) |
| referee_user_id | `REFERRAL_APPLIED` | Unique user ID of the user at your system who referred the current user | String | abcdefgh |
| referee_user_meta | `REFERRAL_APPLIED` | This is the user meta of the person who referred this user. It's format is same as mentioned in the login function for `userMeta` parameter | Json Object | {"email":"johndoe@example.com"} |


Here `actionListener` is the object of interface `MzaaloRewardsRegisterActionListener` that has the following definition:

    interface MzaaloRewardsRegisterActionListener{
	    fun onActionRegistered()
	    fun onError(error: MzError)
    }


Expected values of `code` field in `MzError` object are:

 - 3008
 - 3021
 - 3022
 - 3041
 - 3042

#### Fetch Reward Balance
Call this function if you want to fetch the balance of the user that is currently logged in.

    MzaaloRewards.getBalance(balanceListener)


Here `balanceListener` is the object of interface `MzaaloRewardsBalanceListener` that has the following definition:

    interface MzaaloRewardsBalanceListener{
	    fun onBalanceFetched(balance: Int?)
	    fun onError(error: MzError)
    }

Expected values of `code` field in `MzError` object are:

 - 3008
 - 3041
 - 3042


### player

#### Initialization
Include the Mzaalo's `PlayerView` into the layout file of your activity/fragment.

    <com.xfinite.mzaaloplayer.views.PlayerView  
        android:layout_height="wrap_content"  
        android:layout_width="match_parent"  
        app:layout_constraintLeft_toLeftOf="parent"  
        app:layout_constraintRight_toRightOf="parent"  
        app:layout_constraintTop_toTopOf="parent"/>

<br/>

Declare a variable of `MZVideoPlayer` in your class.

    ...
    private lateinit var mzaaloPlayer: MZVideoPlayer
    ...
<br/>

Now create the `MZVideoPlayer` object using `MzaaloPlayer` top-level class, preferrably in your Activity/Fragment's `onCreate` method, and then initialize it.

    mzaaloPlayer=MzaaloPlayer.createVideoPlayer(context)
    mzaaloPlayer.initialize("CONTENT_ID_THAT_YOU_WANT_TO_PLAY", MzaaloPlayerContentTypes.XXXX, playerInitListener)
 Here `playerInitListener` is the object of the interface `MZVideoPlayerInitListener` that has the following definition:
 

    interface MZVideoPlayerInitListener {  
	    fun onReadyToStart()  
	    fun onError(error: MzError)  
    }


Expected values of `code` field in `MzError` object are:

 - 3008
 - 3031
 - 3032
 - 3041
 - 3042

`MzaaloPlayerContentTypes` is an enum class with the following options:

 - **MzaaloPlayerContentTypes.EPISODE**
 - **MzaaloPlayerContentTypes.MOVIE**

The `initialize` function makes the streaming setup ready to start asynchronously and the callback `onReadyToStart` is fired once the setup is done to play a particular video. At this point, you should call the `start` function on your `mzaaloPlayer` object to start the video playing.

    override fun onReadyToStart(){
	    mzaaloPlayer.start()
    }
After this, your video will start playing.

#### Playback Controls
Following are some common playback control functions that you can invoke to handle the controls of the player.

 - `fun pause()`
	 This allows you to pause the video playback
	 
 - `fun resume()`
	 This allows you to resume the already paused video playback
	 
 - `fun seekTo(pos:Long)`
	 You can seek to any particular position by passing the `pos` parameter in milliseconds.
 - `fun getDuration():Long`
	 This returns the total duration(in milliseconds) of the current video that is loaded on the player.
 - `fun getCurrentPosition():Long`
	 This returns the current cursor position(in milliseconds) of the video playback.
 - `fun isPlaying():Boolean`
	 This return `true` if currently the player is playing and `false` if not.
 - `fun setPlaybackControllerCallback{}`
	 This allows to set a callback based on the states of the player at different times. This function accepts a lambda function that is passed `PlaybackControllerState` object and a `String` error as arguments to the function. This lambda function is called whenever the state of the player changes. `PlaybackControllerState` is an enum class with the following options representing the corresponding player states:
	 - `PlaybackControllerState.STATE_BUFFERING`
	 - `PlaybackControllerState.STATE_IDLE`
	 - `PlaybackControllerState.STATE_ENDED`
	 - `PlaybackControllerState.STATE_READY`
	 - `PlaybackControllerState.STATE_PREPARED`
	 - `PlaybackControllerState.STATE_ERROR`
	
	
#### Cleanup
Finally in your Activity/Fragment's onStop method, call the `clean` function of the `MZVideoPlayer` class to free up the resources:

    override fun onStop() {  
	    mzaaloPlayer.clean()  
	    super.onStop()  
	}
	
<br/>

## Sequence Flow
### mzaalo-auth
![Sequence flow diagram for authentication flow of Mzaalo SDK](https://xfinitesite.blob.core.windows.net/flow-diagrams/mzaalo-auth.png)


### mzaalo-rewards
![Register Rewards action function of the rewards module of the Mzaalo SDK](https://xfinitesite.blob.core.windows.net/flow-diagrams/mzaalo-rewards-rra.png)

![Fetch rewards balance function of the rewards module of Mzaalo SDK](https://xfinitesite.blob.core.windows.net/flow-diagrams/mzaalo-rewards-frb.png)



## References

### Error Codes

The `MzError` object returned in each of the error callbacks has the following fields:

 - `code`: Int
 - `message`:String

The value of  `code` is able to distinguish between the type of the error that is received. Following table illustrates this:
| code | Description |
|--|--|
| 3001 | Invalid or missing partner code |
| 3011 | Invalid phone |
| 3012 | Invalid or missing user ID |
| 3013 | Invalid or missing country code |
| 3014 | Validation Error with the user meta data |
| 3015 | Invalid or missing email |
| 3016 | User ID and user meta mismatch |
| 3021 | Invalid reward action type |
| 3022 | Invalid action meta data |
| 3031 | Invalid or empty content ID |
| 3032 | Could not found streaming playable URL |
| 3041 | Initialization required. This happens when initialization was not completed successfully |
| 3042 | Login required. This happens when login was not completed successfully |
| 3091 | Invalid response from server |
| 3008 | Unclassified general server error |

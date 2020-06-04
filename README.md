# Mzaalo Android SDK
This is the official documentation for the integration of Mzaalo android SDKs in any android application with a valid partner code.

## Table of contents

 - [Overview](#overview)
 - [Installation](#installation)
	 - [Requirements](#requirements)
	 - [Configuration](#configuration)
- [Getting Started](#getting-started)
- [Features and Implementation](#features-and-implementation)

## Overview
Mzaalo SDKs have two modules:

 1. **mzaalo-auth** : This module contains authentication features like login, logout, etc.
 2. **mzaalo-rewards** : This module contains all the authentication features, plus features of rewards like adding rewards, fetching balance, etc.

Both these modules are shippable as separate gradle/maven dependencies.
Structurally, `mzaalo-auth` is the subset of `mzaalo-rewards`. This means, any application that includes the dependency for `mzaalo-rewards` automatically gets the functionality for `mzaalo-auth` out of the box.

    
## Installation

### Requirements

 - Android 4.1 (API Level 16) and above
 - [AndroidX](https://developer.android.com/jetpack/androidx/)

### Configuration
Add `mzaalo-rewards` or `mzaalo-auth` to the application level `build.gradle` file:

    dependencies{
	    ...
	    implementation 'com.xfinite.mzaalo:mzaalo-xxxx:0.0.1'
	    ...
    }

where, **`xxxx`** can be `auth` or `rewards`

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

The entry point to the SDK is through the `init` function that gets called with a valid partner code, a callback based Mzaalo interface object, and an identifier for the environment type(STAGING or PRODUCTION).

    MzaaloRewards.init(context, "YOUR_PARTNER_CODE", initListener, MzaaloEnvironment.XXXX)

Here `initListener` can be either a `MzaaloAuthInitListener` or `MzaaloRewardsInitListener`(depending upon the dependency included) object with the following definition.

    interface MzaaloRewardsInitListener{
	    fun onSuccess()
	    fun onError(error:String)
    }



`MzaaloEnvironment` is an enum class with the following options:

 - **MzaaloEnvironment.STAGING**
 - **MzaaloEnvironment.PRODUCTION**


## Features and Implementation
### Login
Your application should call the `MzaaloAuth.login()` function as soon as the user is identified at your end.

	

    val userMeta=JSONObject()
    userMeta.put(userProperty, value)
    MzaaloAuth.login("UNIQUE_ID_OF_YOUR_USER", userMeta, loginListener)

Here are the valid `userProperty` fields that can put as keys in the `userMeta` json:
|userProperty|Description|Example|
|--|--|--|
|email|Email Address of the user|johndoe@example.com|
|phone|Phone number of the user|9876543210|
|country_code|Country code of the user's phone number|+91, +44|


Here `loginListener` is the object interface `MzaaloAuthLoginListener` that has the following implementation:

    interface MzaaloAuthLoginListener{
	    fun onLoginSuccess(user:User)
	    fun onError(error:String)
    }



### Logout
Your application should call `MzaaloAuth.logout()` function when the user logs out from your application or when the user identitiy is no longer available to you.

    MzaaloAuth.logout()


### Register Rewards Action
This is a feature that allows the application to register an action to the Mzaalo SDK, that should credit some rewards to the user.

    MzaaloRewards.registerRewardAction(MzaaloRewardsActionTypes.XXXX, eventMeta, actionListener)


`MzaaloRewardsActionTypes` is an enum class that describes the type of action that the user has performed. The enum has following options:
| Enum Value | Description |
|--|--|
| `MzaaloRewardsActionTypes.CONTENT_VIEWED` | Send this if you want to give rewards to the user for watching content |
| `MzaaloRewardsActionTypes.CHECKED_IN` | Send this if you want to give rewards to the user for **launching the app** or **visiting some section of the app** on a daily basis |
| `MzaaloRewardsActionTypes.SIGNED_UP` | Send this if you want to give reward to the user for signing up on your application. In this case, call this once the above mentioned login function has been successfully executed. |



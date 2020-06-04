# Mzaalo Android SDK
This is the official documentation for the integration of Mzaalo android SDKs in any android application with a valid partner code.

## Table of contents

 - [Overview](#overview)
 - [Installation](#installation)
	 - [Requirements](#requirements)
	 - [Configuration](#configuration)
- [Getting Started](#getting-started)

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

Here `initListener` can be either a `MzaaloAuthInitListener` or `MzaaloRewardsInitListener` object with the following definition.

    interface MzaaloRewardsInitListener{
		//This function is called on successful initialisation of the Mzaalo SDK
		fun onSuccess()

		//This function is called when some error occurs while initializing
		fun onError(error:String)
    }



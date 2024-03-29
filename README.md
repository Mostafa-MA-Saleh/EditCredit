[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EditCredit-green.svg?style=flat)](https://android-arsenal.com/details/1/6952)
[![JitPack](https://img.shields.io/badge/JitPack-3.0.3-blue.svg?style=flat)](https://jitpack.io/#Mostafa-MA-Saleh/EditCredit/3.0.3)
[![Playstore](https://img.shields.io/badge/Playstore-Demo-brightgreen.svg?style=flat)](https://play.google.com/store/apps/details?id=saleh.ma.mostafa.gmail.com.editcreditdemo)
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/donate/?hosted_button_id=9RTEPXAVC99RW)

<br/>

<p align="center"><img src="logo/logo_ldpi.png" alt="Logo" /></p>

Custom EditText for entering Credit Card numbers, this EditText will also 
display the image of the card number type being entered (after entering the second digit).
And supports adding a separator (spaces or dashes) after every four digits.
<br/>This version supports Visa, MasterCard, American Express and Discover cards.

## Installation via Gradle:

<b>Step 1.</b> Add the JitPack repository to your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

<b>Step 2.</b> Add the dependency
```gradle
dependencies {
  ...
  implementation 'com.github.Mostafa-MA-Saleh:EditCredit:3.0.3'
}
```

## Changelog
### v 3.0.3
- Updated the compileSdk to the latest version. 
- Updated kotlin to the latest version.
- Migrated the gradle files to Kotlin DSL.
### v 3.0.1
- Fixed a issue with caret sometimes moving to where it shouldn't.
### v 3.0.0
- Updated the library to more follow kotlin conventions.
- Replaced some methods with kotlin standard equivalents.
- Fixed an issue wherein trying to change the separator style during runtime would sometimes crash the app.
- Added a Java example activity.
- Cleaned up the code a bit.
### v 2.2.0
- Added support for Diners Club cards.
- Removed the deprecated methods and constants.
- Updated dependencies.
- Other minor improvements.
### v 2.1.3
- Updated dependencies.
- Minor enhancements.
### v 2.1.2
- Added instant experience to the demo app.
- Minor enhancements.
### v 2.1.1
- Reduced the drawables' sizes.
- Fixed the blurry card images on some devices.
- Updated the target SDK.
- Added dark mode support to the demo app.
- Other minor enhancements.
### v 2.1.0
- Fixed the card icon size on Android Pie (API 28).
### v 2.0.0
- Added the ability to get the card type.
- Rewrote the library in kotlin.
- Migrated to AndroidX.
### v 1.7.1
- Added support for Discover cards.
### v 1.6.3
- Minor bug fixes.
### v 1.6.1
- Updated the demo app to include the gravity options.
- Added the ability to change the drawable gravity.
- Deprecated setSeparator(int) and replaced it with setSeparator(Separator).
- Deprecated setDisabledCards(int) and replaced it with setDisabledCards(Card...).
- The minSDK is now 14 (because of AppCompat).
### v 1.5.0
- Added support for American Express.
- Added the ability to disable cards.
- Lowered the minSDK to 9.
- Changed the demo app icon.
### v 1.0.1
- Changed the package name.
### v 1.0
- Initial Version.

## Usage

### Java

#### Public Methods

| return | Name/Description |
|:---|:---|
| Card | getCardType()<br/>Returns the current card type. |
| String | getTextWithoutSeparator()<br/>Returns the card number without the separators. |
| boolean | isCardValid()<br/>Validates the entered card number. |
| void | setSeparator(Separator)<br/>Sets the separator style. |
| Separator | getSeparator()<br/>Returns the current separator style. |
| void | setDisabledCards(Card...)<br/>Sets the disabled cards. |
| void | setDrawableGravity(Gravity)<br/>Sets the location of the card drawable. |
| Gravity | getDrawableGravity()<br/>Returns the current location of the card drawable. |

### Kotlin

#### Public Methods

| return | Name/Description |
|:---|:---|
| void | setDisabledCards(Card...)<br/>Sets the disabled cards. |

#### Public Attributes

| Type | Name/Description |Read Only|
|:---|:---|:--:|
| Card | cardType<br/>The current card type. | * |
| String | textWithoutSeparator<br/>The card number without the separators. | * |
| Boolean | isCardValid<br/>Validates the entered card number. | * |
| Separator | separator<br/>The current separator style. | |
| Gravity | drawableGravity<br/>The location of the card drawable. | |

### XML

#### Attributes

| Name | Description | values |
|:---|:---|:---|
| separator | Sets the separator style | no_separator<br/>spaces<br/>dashes |
| drawableGravity | Sets the the location of the card drawable | start<br/>end<br/>left<br/>right |
| disabledCards | Sets disabled cards<br/>this can be multiple values seperated by "\|"<br/>(eg. app:disabledCards="visa\|amex") | none<br/>visa<br/>mastercard<br/>amex<br/>discover<br/>diners |

#### Example

EditCredit can be used just like a normal EditText
```xml
<mostafa.ma.saleh.gmail.com.editcredit.EditCredit
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:disabledCards="visa|amex"
        app:drawableGravity="end"
        app:separator="spaces" />
```

## Donation

If this project helps you, and you wanna buy me a cup of coffee.

[![PayPal](https://cdn.rawgit.com/twolfson/paypal-github-button/1.0.0/dist/button.svg)](https://www.paypal.com/donate/?hosted_button_id=9RTEPXAVC99RW)

## License
```
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
```

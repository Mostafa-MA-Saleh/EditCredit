[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EditCredit-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5859)
[![JitPack](https://img.shields.io/badge/JitPack-1.5.0-blue.svg?style=flat)](https://jitpack.io/#Mostafa-MA-Saleh/EditCredit/1.5.0)
# EditCredit
Custom EditText for entering Credit Card numbers, this EditText will also 
display the image of the card number type being entered (after entering the second digit).
And supports adding a separator (spaces or dashes) after every four digits.
<br/>This version (1.5.0) supports Visa, MasterCard and American Express cards, but more will be added soon.

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
  compile 'com.github.Mostafa-MA-Saleh:EditCredit:1.5.0'
}
```

## Changelog
### v 1.5.0
- Added support for American Express.
- Added the ability to disable cards.
- Lowered the minSDK to 9.
- Changed the demo app icon.
### v 1.0.1
- Changed the package name.
### v 1.0
- Initial Version

## Summary

### XML attributes

| Name | Description | values |
|:---|:---|:---|
| app:separator | Sets the separator style | no_separator<br/>spaces<br/>dashes |
| app:disabledCards | Sets disabled cards<br/>this can be multiple values seperated by "\|"<br/>(eg. app:disabledCards="visa\|amex") | none<br/>visa<br/>mastercard<br/>amex |

### Constants

| type | Name |
|:---|:---|
| int | NO_SEPARATOR |
| int | SPACES_SEPARATOR |
| int | DASHES_SEPARATOR |
| int | NONE |
| int | VISA |
| int | MASTERCARD |
| int | AMEX |

### Public Methods

| return | Name/Description |
|:---|:---|
| String | getTextWithoutSeparator()<br/>Returns the card number without the separators. |
| boolean | isCardValid()<br/>Validates the entered card number. |
| void | setSeparator(int separator)<br/>Sets the separator style. Should be NO_SEPARATOR, SPACES_SEPARATOR or DASHES_SEPARATOR. |
| void | setDisabledCards(int disabledCards)<br/>Sets the disabled cards. Should be NONE, VISA, MASTERCARD or AMEX.<br/>If you want to disable more than one type of card add "\|" between each one.<br/>(eg. setDisabledCards(EditCredit.VISA\|EditCredit.AMEX);) |


## Usage

EditCredit can be used just like a normal EditText
```xml
<mostafa.ma.saleh.gmail.com.editcredit.EditCredit
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:disabledCards="visa|amex"
        app:separator="spaces" />
```

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

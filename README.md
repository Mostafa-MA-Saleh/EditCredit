# EditCredit
Custom EditText for entering Credit Card numbers, this EditText will also 
display the image of the card number type being entered (after entering the second digit).
And supports adding a separator (spaces or dashes) after every four digits.
<br/>In this version (1.0.1) only Visa and MasterCard cards are supported, but more will be added soon.

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
  compile 'com.github.Mostafa-MA-Saleh:EditCredit:1.0.1'
}
```


## Summary

### XML attributes

| Name | Description | values |
|:---|:---|:---|
| app:separator | Sets the separator style | no_separator<br/>spaces<br/>dashes |

### Constants

| type | Name |
|:---|:---|
| int | NO_SEPARATOR |
| int | SPACES_SEPARATOR |
| int | DASHES_SEPARATOR |

### Public Methods

| return | Name/Description |
|:---|:---|
| String | getTextWithoutSeparator()<br/>Returns the card number without the separators. |
| boolean | isCardValid()<br/>Validates the entered card number. |
| void | setSeparator(int separator)<br/>Sets the separator style. Should be NO_SEPARATOR, SPACES_SEPARATOR or DASHES_SEPARATOR. |


## Usage

EditCredit can be used just like a normal EditText
```xml
<mostafa.ma.saleh.gmail.com.editcredit.EditCredit
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:separator="spaces"/>
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

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

package mostafa.ma.saleh.gmail.com.editcredit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.SparseArray
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import java.util.regex.Pattern
import kotlin.properties.Delegates

class EditCredit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var mCCPatterns = SparseArray<Pattern>()
    private var isValidCard: Boolean = false
    private var mCurrentDrawableResId = Card.UNKNOWN.drawableRes
    private val maxLength = 16

    /**
     * This property sets the location of the card drawable.
     * The default gravity is [Gravity.END].
     */
    var drawableGravity: Gravity by Delegates.observable(Gravity.END) { _, _, _ ->
        addDrawable()
    }

    /**
     * This property sets the separator style.
     * The default separator is [Separator.NONE].
     */
    var separator: Separator by Delegates.observable(Separator.NONE) { _, oldValue, newValue ->
        filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength + (newValue.length * (maxLength / 4))))
        keyListener = DigitsKeyListener.getInstance("0123456789$newValue")
        val textWithoutSeparator = text.toString().replace(oldValue.toRegex(), "")
        val caretPosition = selectionStart + when (newValue == oldValue) {
            false -> (newValue.length * (selectionStart / 4))
            true -> if ((selectionStart - (selectionStart / 4)) % 4 == 0) 1 else 0
        }
        updateText(
            textWithoutSeparator.chunked(4)
                .joinToString("") { "$it$newValue" }
                .removeSuffix("$newValue")
        )
        if (caretPosition < text?.length ?: 0)
            setSelection(caretPosition)
    }

    val textWithoutSeparator
        get() = when (separator) {
            Separator.NONE -> text.toString()
            else -> text.toString().replace(separator.toRegex(), "")
        }

    val isCardValid: Boolean
        get() = textWithoutSeparator.length > 12 && isValidCard

    val cardType: Card
        get() = Card.from(mCurrentDrawableResId)

    enum class Separator(private val stringValue: String) {
        NONE(""), SPACES(" "), DASHES("-");

        override fun toString() = stringValue

        internal fun toRegex() = stringValue.toRegex()

        internal val length
            get() = stringValue.length
    }

    enum class Gravity {
        START, END, LEFT, RIGHT
    }

    enum class Card(
        internal val value: Int,
        @field:DrawableRes internal val drawableRes: Int,
        internal val regex: Regex
    ) {
        VISA(1, R.drawable.visa, Regex("^4[0-9]{1,12}(?:[0-9]{6})?$")),
        MASTERCARD(2, R.drawable.mastercard, Regex("^5[1-5][0-9]{0,14}$")),
        AMEX(4, R.drawable.amex, Regex("^3[47][0-9]{0,13}$")),
        DISCOVER(8, R.drawable.discover, Regex("^6(?:011|5[0-9]{1,2})[0-9]{0,12}$")),
        DINERS(16, R.drawable.diners, Regex("^3(?:0[0-5]|[68][0-9])[0-9]{0,11}\$")),
        UNKNOWN(-1, R.drawable.creditcard, Regex(".*"));

        companion object {
            internal fun from(@DrawableRes drawableRes: Int) =
                values().find { it.drawableRes == drawableRes } ?: UNKNOWN
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(
            text: CharSequence,
            start: Int,
            lengthBefore: Int,
            lengthAfter: Int
        ) {
            var mDrawableResId = 0
            for (i in 0 until mCCPatterns.size()) {
                val key = mCCPatterns.keyAt(i)

                val p = mCCPatterns.get(key)

                val m = p.matcher(textWithoutSeparator)
                isValidCard = m.find()
                if (isValidCard) {
                    mDrawableResId = key
                    break
                }
            }
            if (mDrawableResId != 0 && mDrawableResId != mCurrentDrawableResId) {
                mCurrentDrawableResId = mDrawableResId
            } else if (mDrawableResId == 0) {
                mCurrentDrawableResId = Card.UNKNOWN.drawableRes
            }
            addDrawable()
            separator = separator
        }
    }

    init {
        enableAllCards()
        inputType = InputType.TYPE_CLASS_PHONE
        separator = Separator.NONE
        drawableGravity = Gravity.END
        attrs?.let { applyAttributes(it) }
        addTextChangedListener(textWatcher)
    }

    private fun applyAttributes(attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EditCredit,
            0, 0
        )
        try {
            separator = Separator.values()[a.getInt(
                R.styleable.EditCredit_separator,
                Separator.NONE.ordinal
            )]
            setDisabledCards(
                *Card.values().filter {
                    containsFlag(
                        a.getInt(R.styleable.EditCredit_disabledCards, 0),
                        it.value
                    )
                }.toTypedArray()
            )
            drawableGravity = Gravity.values()[a.getInt(
                R.styleable.EditCredit_drawableGravity,
                Gravity.END.ordinal
            )]
        } finally {
            a.recycle()
        }
    }

    private fun addDrawable() {
        ContextCompat.getDrawable(context, mCurrentDrawableResId)
            ?.takeIf { error.isNullOrEmpty() }
            ?.resize()
            ?.let {
                when (drawableGravity) {
                    Gravity.START -> setDrawablesRelative(start = it)
                    Gravity.RIGHT -> setDrawables(right = it)
                    Gravity.LEFT -> setDrawables(left = it)
                    else -> setDrawablesRelative(end = it)
                }
            }
    }

    private fun updateText(newText: String) {
        removeTextChangedListener(textWatcher)
        setText("")
        append(newText)
        addTextChangedListener(textWatcher)
    }

    /**
     * Use this method to set which cards are disabled.
     * By default all supported cards are enabled.
     *
     * @param cards the cards to be disabled.
     */
    fun setDisabledCards(vararg cards: Card) {
        mCCPatterns.clear()
        for (card in Card.values()) {
            if (card.value == -1 || cards.contains(card)) continue
            mCCPatterns.put(card.drawableRes, Pattern.compile(card.regex.toString()))
        }
        textWatcher.onTextChanged("", 0, 0, 0)
    }

    /**
     * Use this method to enable all supported cards.
     *
     */
    fun enableAllCards() = setDisabledCards()

    private fun containsFlag(flagSet: Int, flag: Int): Boolean {
        return flagSet or flag == flagSet
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (compoundDrawables.all { it == null }) {
            addDrawable()
        }
    }

    private fun Drawable.resize() =
        when (val height = measuredHeight - (paddingTop + paddingBottom)) {
            in 1 until intrinsicHeight -> {
                val bitmap = (this as BitmapDrawable).bitmap
                val ratio = getIntrinsicWidth().toFloat() / intrinsicHeight.toFloat()
                val resizedBitmap =
                    Bitmap.createScaledBitmap(bitmap, (height * ratio).toInt(), height, false)
                resizedBitmap.density = Bitmap.DENSITY_NONE
                BitmapDrawable(resources, resizedBitmap)
            }
            in Int.MIN_VALUE..0 -> null
            else -> this
        }

    private fun setDrawablesRelative(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) = TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
        this,
        start,
        top,
        end,
        bottom
    )

    private fun setDrawables(
        left: Drawable? = null,
        top: Drawable? = null,
        right: Drawable? = null,
        bottom: Drawable? = null
    ) = setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}
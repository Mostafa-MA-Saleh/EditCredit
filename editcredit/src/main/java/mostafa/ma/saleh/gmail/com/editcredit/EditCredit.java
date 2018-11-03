package mostafa.ma.saleh.gmail.com.editcredit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCredit extends AppCompatEditText {

    public enum Separator {
        NONE, SPACES, DASHES
    }

    public enum Gravity {
        START, END, LEFT, RIGHT
    }

    public enum Card {
        VISA(1), MASTERCARD(2), AMEX(4), DISCOVER(8);
        private int value;

        Card(int value) {
            this.value = value;
        }
    }

    @Deprecated
    public static final int NO_SEPARATOR = 0;
    @Deprecated
    public static final int SPACES_SEPARATOR = 1;
    @Deprecated
    public static final int DASHES_SEPARATOR = 2;

    @Deprecated
    public static final int NONE = 0;
    @Deprecated
    public static final int VISA = 1;
    @Deprecated
    public static final int MASTERCARD = 2;
    @Deprecated
    public static final int AMEX = 4;
    @Deprecated
    public static final int DISCOVER = 8;

    private SparseArray<Pattern> mCCPatterns = null;

    @Nullable
    private String mSeparator;
    private boolean isValidCard;

    private Gravity mDrawableGravity = Gravity.END;
    private int mCurrentDrawableResId = 0;

    public EditCredit(Context context) {
        super(context);
        init();
    }

    public EditCredit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        applyAttributes(attrs);
    }

    public EditCredit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        applyAttributes(attrs);
    }

    private void init() {
        if (mCCPatterns == null) {
            setDisabledCards();
        }
        setInputType(InputType.TYPE_CLASS_PHONE);
        setSeparator(Separator.NONE);
        setDrawableGravity(Gravity.END);
    }

    private void applyAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditCredit,
                0, 0);
        try {
            setSeparator(Separator.values()[a.getInt(R.styleable.EditCredit_separator, Separator.NONE.ordinal())]);
            setDisabledCardsInternal(a.getInt(R.styleable.EditCredit_disabledCards, 0));
            setDrawableGravity(Gravity.values()[a.getInt(R.styleable.EditCredit_drawableGravity, Gravity.END.ordinal())]);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

        if (mCCPatterns == null) {
            init();
        }

        String textWithoutSeparator = getTextWithoutSeparator();

        int mDrawableResId = 0;
        for (int i = 0; i < mCCPatterns.size(); i++) {
            int key = mCCPatterns.keyAt(i);

            Pattern p = mCCPatterns.get(key);

            Matcher m = p.matcher(textWithoutSeparator);
            if (isValidCard = m.find()) {
                mDrawableResId = key;
                break;
            }
        }
        if (mDrawableResId > 0 && mDrawableResId != mCurrentDrawableResId) {
            mCurrentDrawableResId = mDrawableResId;
        } else if (mDrawableResId == 0) {
            mCurrentDrawableResId = R.drawable.creditcard;
        }
        addDrawable();
        addSeparators();
    }

    private void addDrawable() {
        Drawable currentDrawable = ContextCompat.getDrawable(getContext(), mCurrentDrawableResId);
        if (currentDrawable != null && TextUtils.isEmpty(getError())) {
            currentDrawable = resize(currentDrawable);
            if (mDrawableGravity == null) {
                mDrawableGravity = Gravity.END;
            }
            switch (mDrawableGravity) {
                case START:
                    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, currentDrawable, null, null, null);
                    break;
                case RIGHT:
                    setCompoundDrawablesWithIntrinsicBounds(null, null, currentDrawable, null);
                    break;
                case LEFT:
                    setCompoundDrawablesWithIntrinsicBounds(currentDrawable, null, null, null);
                    break;
                default:
                    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, null, null, currentDrawable, null);
                    break;
            }
        }
    }

    private void addSeparators() {
        String text = getText().toString();
        if (mSeparator != null) {
            if (text.length() > 4 && !text.matches("(?:[0-9]{4}" + mSeparator + ")+[0-9]{1,4}")) {
                StringBuilder sp = new StringBuilder();
                int caretPosition = getSelectionEnd();
                String[] segments = splitString(text.replaceAll(mSeparator, ""));
                for (String segment : segments) {
                    sp.append(segment).append(mSeparator);
                }
                setText("");
                append(sp.delete(sp.length() - mSeparator.length(), sp.length()).toString());
                if (caretPosition < text.length())
                    setSelection(caretPosition);
            }
        }
    }

    private void removeSeparators() {
        String text = getText().toString();
        text = text.replaceAll(" ", "").replaceAll("-", "");
        setText("");
        append(text);
    }

    private String[] splitString(String s) {
        int arrayLength = (int) Math.ceil(((s.length() / (double) 4)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + 4);
            j += 4;
        }
        result[lastIndex] = s.substring(j);

        return result;
    }

    public String getTextWithoutSeparator() {
        if (mSeparator == null) return getText().toString();
        return getText().toString().replaceAll(mSeparator, "");
    }

    /**
     * This method has been deprecated, please use {@link #setSeparator(Separator)} instead.
     */
    @Deprecated
    public void setSeparator(@IntRange(from = 0, to = 2) int separator) {
        if (separator > 2 || separator < 0)
            throw new IllegalArgumentException("The separator has to be one of the following:" +
                    "NO_SEPARATOR." +
                    "SPACES_SEPARATOR." +
                    "DASHES_SEPARATOR.");
        setSeparator(Separator.values()[separator]);
    }

    /**
     * Use this method to set the separator style.
     * The default separator is {@link Separator#NONE}.
     *
     * @param separator the style of the separator.
     */
    public void setSeparator(@NonNull Separator separator) {
        switch (separator) {
            case NONE:
                mSeparator = null;
                break;
            case SPACES:
                mSeparator = " ";
                break;
            case DASHES:
                mSeparator = "-";
                break;
        }
        if (mSeparator != null) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(23)});
            setKeyListener(DigitsKeyListener.getInstance("0123456789" + mSeparator));
            addSeparators();
        } else {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
            setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            removeSeparators();
        }
    }

    /**
     * Use this method to set the location of the card drawable.
     * The default gravity is {@link Gravity#END}.
     *
     * @param gravity the drawable location.
     */
    public void setDrawableGravity(@NonNull Gravity gravity) {
        mDrawableGravity = gravity;
        addDrawable();
    }

    private void setDisabledCardsInternal(int disabledCards) {
        List<Card> cards = new ArrayList<>();
        if (containsFlag(disabledCards, Card.VISA.value)) {
            cards.add(Card.VISA);
        }
        if (containsFlag(disabledCards, Card.MASTERCARD.value)) {
            cards.add(Card.MASTERCARD);
        }
        if (containsFlag(disabledCards, Card.AMEX.value)) {
            cards.add(Card.AMEX);
        }
        if (containsFlag(disabledCards, Card.DISCOVER.value)) {
            cards.add(Card.DISCOVER);
        }
        setDisabledCards(cards.toArray(new Card[0]));
    }

    /**
     * This method has been deprecated, please use {@link #setDisabledCards(Card...)}} instead.
     */
    @Deprecated
    public void setDisabledCards(int disabledCards) {
        setDisabledCardsInternal(disabledCards);
    }

    /**
     * Use this method to set which cards are disabled.
     * By default all supported cards are enabled.
     *
     * @param cards the cards to be disabled.
     */
    public void setDisabledCards(Card... cards) {
        mCCPatterns = new SparseArray<>();
        int disabledCards = 0;
        if (cards != null) {
            for (Card card : cards) {
                disabledCards |= card.value;
            }
        }
        if (!containsFlag(disabledCards, Card.VISA.value)) {
            mCCPatterns.put(R.drawable.visa, Pattern.compile("^4[0-9]{1,12}(?:[0-9]{6})?$"));
        }
        if (!containsFlag(disabledCards, Card.MASTERCARD.value)) {
            mCCPatterns.put(R.drawable.mastercard, Pattern.compile("^5[1-5][0-9]{0,14}$"));
        }
        if (!containsFlag(disabledCards, Card.AMEX.value)) {
            mCCPatterns.put(R.drawable.amex, Pattern.compile("^3[47][0-9]{0,13}$"));
        }
        if (!containsFlag(disabledCards, Card.DISCOVER.value)) {
            mCCPatterns.put(R.drawable.discover, Pattern.compile("^6(?:011|5[0-9]{1,2})[0-9]{0,12}$"));
        }
        onTextChanged("", 0, 0, 0);
    }

    private boolean containsFlag(int flagSet, int flag) {
        return (flagSet | flag) == flagSet;
    }

    public boolean isCardValid() {
        return getTextWithoutSeparator().length() > 12 && isValidCard;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean noDrawablesVisible = true;
        for (Drawable drawable : getCompoundDrawables()) {
            if (drawable != null) {
                noDrawablesVisible = false;
                break;
            }
        }
        if (noDrawablesVisible) {
            addDrawable();
        }
    }

    private Drawable resize(Drawable image) {
        int imageIntrinsicHeight = image.getIntrinsicHeight();
        int height = getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());
        if (height <= 0) {
            return null;
        } else if (imageIntrinsicHeight > height) {
            Bitmap b = ((BitmapDrawable) image).getBitmap();
            float ratio = (float) image.getIntrinsicWidth() / (float) imageIntrinsicHeight;
            Bitmap bitmapResized = Bitmap.createScaledBitmap(b, (int) (height * ratio), height, false);
            return new BitmapDrawable(getResources(), bitmapResized);
        } else {
            return image;
        }
    }
}
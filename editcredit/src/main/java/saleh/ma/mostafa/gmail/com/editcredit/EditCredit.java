package saleh.ma.mostafa.gmail.com.editcredit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCredit extends AppCompatEditText {

    public static final int NO_SEPARATOR = 0;
    public static final int SPACES_SEPARATOR = 1;
    public static final int DASHES_SEPARATOR = 2;

    private SparseArray<Pattern> mCCPatterns = null;

    private @Nullable String mSeparator;

    private boolean isValidCard;

    private int mCurrentDrawableResId = 0;
    private Drawable mCurrentDrawable;

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
            mCCPatterns = new SparseArray<>();
            mCCPatterns.put(R.drawable.visa, Pattern.compile("^4[0-9]{1,12}(?:[0-9]{6})?$"));
            mCCPatterns.put(R.drawable.mastercard, Pattern.compile("^5[1-5][0-9]{0,14}$"));
        }
        setInputType(InputType.TYPE_CLASS_PHONE);
        setSeparator(NO_SEPARATOR);
    }

    private void applyAttributes(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditCredit,
                0, 0);
        try {
            setSeparator(a.getInt(R.styleable.EditCredit_separator, NO_SEPARATOR));
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

        mCurrentDrawable = ContextCompat.getDrawable(getContext(), mCurrentDrawableResId);
        addSeparators();
    }

    private void addSeparators() {
        String text = getText().toString();
        if (mSeparator != null) {
            if (text.length() > 4 && !text.matches("(?:[0-9]{4}" + mSeparator + ")+[0-9]{1,4}")){
                StringBuilder sp = new StringBuilder();
                int caretPosition = getSelectionEnd();
                String[] segments = splitStringEvery(text.replaceAll(mSeparator, ""), 4);
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

    private void removeSeparators(){
        String text = getText().toString();
        text = text.replaceAll(" ", "").replaceAll("-", "");
        setText("");
        append(text);
    }

    private String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        }
        result[lastIndex] = s.substring(j);

        return result;
    }

    public String getTextWithoutSeparator(){
        if (mSeparator == null) return getText().toString();
        return getText().toString().replaceAll(mSeparator, "");
    }

    public void setSeparator(int separator){
        if (separator > 2 || separator < 0)
            throw new IllegalArgumentException("The separator has to be one of the following:" +
                    "NO_SEPARATOR." +
                    "SPACES_SEPARATOR." +
                    "DASHES_SEPARATOR.");
        switch (separator){
            case NO_SEPARATOR:
                mSeparator = null;
                break;
            case SPACES_SEPARATOR:
                mSeparator = " ";
                break;
            case DASHES_SEPARATOR:
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

    public boolean isCardValid() {
        return getTextWithoutSeparator().length() > 12 && isValidCard;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentDrawable == null) {
            return;
        }

        int rightOffset = 0;
        if (getError() != null && getError().length() > 0) {
            rightOffset = (int) getResources().getDisplayMetrics().density * 32;
        }

        int right = getWidth() - getPaddingRight() - rightOffset;
        int top = getPaddingTop();
        int bottom = getHeight() - getPaddingBottom();
        float ratio = (float) mCurrentDrawable.getIntrinsicWidth() / (float) mCurrentDrawable.getIntrinsicHeight();
        int left = (int) (right - ((bottom - top) * ratio));
        mCurrentDrawable.setBounds(left, top, right, bottom);
        mCurrentDrawable.draw(canvas);
    }
}
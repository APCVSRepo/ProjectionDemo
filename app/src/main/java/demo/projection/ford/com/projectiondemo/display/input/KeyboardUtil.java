package demo.projection.ford.com.projectiondemo.display.input;

/**
 * Created by leon on 2018/5/22.
 */

import java.util.List;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import demo.projection.ford.com.projectiondemo.R;

public class KeyboardUtil
{
    private Context ctx;
    private KeyboardView keyboardView;
    private Keyboard k1;
    public boolean isupper = false;// 是否大写
    private EditText ed;
    private OnKeyListener onKeyListener;

    public interface OnKeyListener
    {
        public void OnKeyDone(String value);
    }

    public KeyboardUtil(View view, Context ctx, EditText edit, OnKeyListener onKeyListener)
    {
        this.ctx = ctx;
        this.ed = edit;
        this.onKeyListener = onKeyListener;
        k1 = new Keyboard(ctx, R.xml.qwerty);
        keyboardView = (KeyboardView) view;
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(onKeyboardActionListener);
    }

    private OnKeyboardActionListener onKeyboardActionListener = new OnKeyboardActionListener()
    {
        @Override
        public void swipeUp()
        {
        }

        @Override
        public void swipeRight()
        {
        }

        @Override
        public void swipeLeft()
        {
        }

        @Override
        public void swipeDown()
        {
        }

        @Override
        public void onText(CharSequence text)
        {
        }

        @Override
        public void onRelease(int primaryCode)
        {
        }

        @Override
        public void onPress(int primaryCode)
        {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes)
        {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            switch(primaryCode)
            {
            case Keyboard.KEYCODE_DONE:
                if (onKeyListener != null)
                    onKeyListener.OnKeyDone(editable.toString());
                break;
            case Keyboard.KEYCODE_DELETE: //backspace
                if (editable != null && editable.length() > 0)
                {
                    if (start > 0)
                        editable.delete(start - 1, start);
                }
                break;
            case Keyboard.KEYCODE_SHIFT:
                changeKey();
                keyboardView.setKeyboard(k1);
                break;
            case 57419: //go left
                if (start > 0)
                    ed.setSelection(start - 1);
                break;
            case 57421: // go right
                if (start < ed.length())
                    ed.setSelection(start + 1);
                break;
            default:
                editable.insert(start, Character.toString((char) primaryCode));
                break;
            }

        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey()
    {
        List<Key> keylist = k1.getKeys();
        if (isupper)
        {//大写切换小写
            isupper = false;
            for (Key key : keylist)
            {
                if (key.label != null && isword(key.label.toString()))
                {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        }
        else
        {//小写切换大写
            isupper = true;
            for (Key key : keylist)
            {
                if (key.label != null && isword(key.label.toString()))
                {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard()
    {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE)
        {
            ed.getEditableText().clear();
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard()
    {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE)
        {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isword(String str)
    {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1)
        {
            return true;
        }
        return false;
    }

}
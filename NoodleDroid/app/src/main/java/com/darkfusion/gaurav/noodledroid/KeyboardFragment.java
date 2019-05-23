package com.darkfusion.gaurav.noodledroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.darkfusion.gaurav.noodledroid.utils.CustomKeyEvent;
import com.darkfusion.gaurav.noodledroid.utils.SingleToast;

import java.lang.ref.WeakReference;

public class KeyboardFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    static final Character BACKSPACE = '\b';

    private Button escapeButton;
    private Button homeButton;
    private Button printScreenButton;
    private Button endButton;
    private ToggleButton numLockButton;
    private ToggleButton capsLockButton;
    private ToggleButton scrollLockButton;

    private Button functionKeyOne;
    private Button functionKeyTwo;
    private Button functionKeyThree;
    private Button functionKeyFour;
    private Button functionKeyFive;
    private Button functionKeySix;
    private Button functionKeySeven;
    private Button functionKeyEight;
    private Button functionKeyNine;
    private Button functionKeyTen;
    private Button functionKeyEleven;
    private Button functionKeyTwelve;

    private ToggleButton controlButton;
    private ToggleButton altButton;
    private ToggleButton shiftButton;
    private ToggleButton windowsButton;
    private Button menuButton;
    private Button enterButton;

    private TextWatcher textWatcher = new TextWatcher() {
        CharSequence previousText = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //TODO NOTHING
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            KeyboardCommunicationAsyncTask communicationAsyncTask = new KeyboardCommunicationAsyncTask(getContext());
            char ch = getTypedCharacter(s, previousText);

            //Sends backspace instruction to keyboard
            if (ch == BACKSPACE) {
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_DEL));
                return;
            }

            //If not backspace, transmit the character as is
            communicationAsyncTask.execute(String.valueOf(ch));
        }

        @Override
        public void afterTextChanged(Editable s) {
            previousText = s.toString();
        }

        //#region Doesn't collapse automatically since it is in anonymous class. Hence adding manual "region"

        /**
         * Uses the current text and previously recorded text of the EditText
         * to determine the typed character.
         *
         * Scenario one: Current text is longer than the previous one.
         *               The typed character is present in the index
         *               which is equal to the length of previous text.
         *      Example: Current: "WORLD"
         *               Previous: "WORL"
         *               Length of previous: 4
         *               Typed Character: Current.charAt(4) (i.e., 'D')
         *
         * Scenario two: Current text is shorter than previous text
         *               Its obvious that a BACKSPACE had been "typed"
         *               since it reduced the length of the current text
         *               making it shorter than previous
         *      Example: Current: "WORL"
         *               Previous: "WORLD"
         *               Current is shorter than previous, hence BACKSPACE
         *
         * @param currentText The current text in the view EditText
         * @param previousText The previously recorded text in the view EditText
         * @return The character that was typed
         */
        //#endregion
        char getTypedCharacter(CharSequence currentText, CharSequence previousText) {
            int currentTextLength = currentText.length();
            int prevTextLength = previousText.length();

            if (currentTextLength >= prevTextLength) {
                return currentText.charAt(prevTextLength);
            } else {
                return BACKSPACE;
            }
        }
    };

    public static KeyboardFragment newInstance() {
        return new KeyboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        System.out.println("YAY VIEW BEING CREATED!");
        return inflater.inflate(R.layout.keyboard_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fetchAllButtonsFromLayout();
        setOnClickListenerForAllButtons();
        setKeyListenersForSoftKeyboard();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        KeyboardCommunicationAsyncTask communicationAsyncTask = new KeyboardCommunicationAsyncTask(getContext());
        switch (v.getId()) {
            case R.id.escapeButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_ESCAPE));
                break;

            case R.id.homeButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(CustomKeyEvent.CUSTOM_KEYCODE_HOME));
                break;

            case R.id.printScreenButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(CustomKeyEvent.CUSTOM_KEYCODE_PRINT_SCREEN));
                break;

            case R.id.endButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(CustomKeyEvent.CUSTOM_KEYCODE_END));
                break;

            case R.id.numLockButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_NUM_LOCK));
                break;

            case R.id.capsLockButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_CAPS_LOCK));
                break;

            case R.id.scrollLockButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_SCROLL_LOCK));
                break;

            case R.id.functionKey_1:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F1));
                break;

            case R.id.functionKey_2:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F2));
                break;

            case R.id.functionKey_3:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F3));
                break;

            case R.id.functionKey_4:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F4));
                break;

            case R.id.functionKey_5:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F5));
                break;

            case R.id.functionKey_6:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F6));
                break;

            case R.id.functionKey_7:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F7));
                break;

            case R.id.functionKey_8:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F8));
                break;

            case R.id.functionKey_9:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F9));
                break;

            case R.id.functionKey_10:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F10));
                break;

            case R.id.functionKey_11:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F11));
                break;

            case R.id.functionKey_12:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_F12));
                break;

            case R.id.ctrlButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_CTRL_LEFT));
                break;

            case R.id.altButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_ALT_LEFT));
                break;

            case R.id.shiftButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_SHIFT_LEFT));
                break;

            case R.id.windowsKey:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(CustomKeyEvent.CUSTOM_KEYCODE_WINDOWS));
                break;

            case R.id.menuButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_MENU));
                break;
            case R.id.enterButton:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(KeyEvent.KEYCODE_ENTER));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            buttonView.setTextColor(getResources().getColor(R.color.buttonActiveColor));
        } else {
            buttonView.setTextColor(getResources().getColor(R.color.buttonInactiveColor));
        }
    }

    static class KeyboardCommunicationAsyncTask extends AsyncTask<String, Void, Boolean> {
        WeakReference<Context> contextWeakReference;

        KeyboardCommunicationAsyncTask(Context context) {
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(String... keyStrokes) {
            if (MainActivity.communicationHandler == null) {
                return false;
            }
            MainActivity.communicationHandler.sendKeyStroke(keyStrokes);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean taskSuccessful) {
            if (!taskSuccessful) {
                showServerDisconnectedToast();
            }

            super.onPostExecute(taskSuccessful);
        }

        private void showServerDisconnectedToast() {
            Context context = contextWeakReference.get().getApplicationContext();
            SingleToast.show(context,
                    context.getResources().getString(R.string.serverDisconnected),
                    Toast.LENGTH_LONG);
        }
    }

    /**
     * Called from the onKeyDown() from the class MainActivity (overrides from AppCompatActivity)
     * Handles keyDown events (Mostly the backspace key in the soft keyboard)
     *
     * @param keyCode keyCode of the key that went down
     * @param context the context of the event
     */
    static void customOnKeyDown(int keyCode, Context context) {
        KeyboardCommunicationAsyncTask communicationAsyncTask = new KeyboardCommunicationAsyncTask(context);
        communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(keyCode));
    }

    void fetchAllButtonsFromLayout() {
        View rootView = getView();
        if (rootView == null) {
            SingleToast.show(getContext(), "Error launching the keyboard", Toast.LENGTH_LONG);
            return;
        }

        escapeButton = rootView.findViewById(R.id.escapeButton);
        homeButton = rootView.findViewById(R.id.homeButton);
        endButton = rootView.findViewById(R.id.endButton);
        printScreenButton = rootView.findViewById(R.id.printScreenButton);

        numLockButton = rootView.findViewById(R.id.numLockButton);
        capsLockButton = rootView.findViewById(R.id.capsLockButton);
        scrollLockButton = rootView.findViewById(R.id.scrollLockButton);

        functionKeyOne = rootView.findViewById(R.id.functionKey_1);
        functionKeyTwo = rootView.findViewById(R.id.functionKey_2);
        functionKeyThree = rootView.findViewById(R.id.functionKey_3);
        functionKeyFour = rootView.findViewById(R.id.functionKey_4);
        functionKeyFive = rootView.findViewById(R.id.functionKey_5);
        functionKeySix = rootView.findViewById(R.id.functionKey_6);
        functionKeySeven = rootView.findViewById(R.id.functionKey_7);
        functionKeyEight = rootView.findViewById(R.id.functionKey_8);
        functionKeyNine = rootView.findViewById(R.id.functionKey_9);
        functionKeyTen = rootView.findViewById(R.id.functionKey_10);
        functionKeyEleven = rootView.findViewById(R.id.functionKey_11);
        functionKeyTwelve = rootView.findViewById(R.id.functionKey_12);

        controlButton = rootView.findViewById(R.id.ctrlButton);
        altButton = rootView.findViewById(R.id.altButton);
        shiftButton = rootView.findViewById(R.id.shiftButton);
        windowsButton = rootView.findViewById(R.id.windowsKey);
        menuButton = rootView.findViewById(R.id.menuButton);

        enterButton = rootView.findViewById(R.id.enterButton);
    }

    void setOnClickListenerForAllButtons() {
        escapeButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        printScreenButton.setOnClickListener(this);
        endButton.setOnClickListener(this);

        numLockButton.setOnCheckedChangeListener(this);
        numLockButton.setOnClickListener(this);
        capsLockButton.setOnCheckedChangeListener(this);
        capsLockButton.setOnClickListener(this);
        scrollLockButton.setOnCheckedChangeListener(this);
        scrollLockButton.setOnClickListener(this);

        functionKeyOne.setOnClickListener(this);
        functionKeyTwo.setOnClickListener(this);
        functionKeyThree.setOnClickListener(this);
        functionKeyFour.setOnClickListener(this);
        functionKeyFive.setOnClickListener(this);
        functionKeySix.setOnClickListener(this);
        functionKeySeven.setOnClickListener(this);
        functionKeyEight.setOnClickListener(this);
        functionKeyNine.setOnClickListener(this);
        functionKeyTen.setOnClickListener(this);
        functionKeyEleven.setOnClickListener(this);
        functionKeyTwelve.setOnClickListener(this);

        controlButton.setOnCheckedChangeListener(this);
        controlButton.setOnClickListener(this);
        altButton.setOnCheckedChangeListener(this);
        altButton.setOnClickListener(this);
        shiftButton.setOnCheckedChangeListener(this);
        shiftButton.setOnClickListener(this);
        windowsButton.setOnCheckedChangeListener(this);
        windowsButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        enterButton.setOnClickListener(this);
    }

    void setKeyListenersForSoftKeyboard() {
        View view = getView();
        if (view == null) {
            return;
        }

        EditText editText = view.findViewById(R.id.keyboardEditText);
        editText.addTextChangedListener(textWatcher);
    }
}

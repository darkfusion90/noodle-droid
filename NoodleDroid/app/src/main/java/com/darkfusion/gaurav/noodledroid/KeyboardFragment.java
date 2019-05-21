package com.darkfusion.gaurav.noodledroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import static com.darkfusion.gaurav.noodledroid.MainActivity.communicationHandler;

public class KeyboardFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    static final Character BACKSPACE = '\b';

    Button escapeButton;
    Button homeButton;
    Button printScreenButton;
    Button endButton;
    ToggleButton numLockButton;
    ToggleButton capsLockButton;
    ToggleButton scrollLockButton;

    Button functionKeyOne;
    Button functionKeyTwo;
    Button functionKeyThree;
    Button functionKeyFour;
    Button functionKeyFive;
    Button functionKeySix;
    Button functionKeySeven;
    Button functionKeyEight;
    Button functionKeyNine;
    Button functionKeyTen;
    Button functionKeyEleven;
    Button functionKeyTwelve;

    ToggleButton controlButton;
    ToggleButton altButton;
    ToggleButton shiftButton;
    ToggleButton windowsButton;
    Button menuButton;
    Button enterButton;

    EditText editText;
    private TextWatcher textWatcher = new TextWatcher() {
        CharSequence previousText = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //TODO NOTHING
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CommunicationAsyncTask communicationAsyncTask = new CommunicationAsyncTask(getContext());

            char ch = getTypedCharacter(s, previousText);
            if (ch == BACKSPACE) {
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_DEL));
                return;
            }
            Log.d(MainActivity.LOG_TAG, "Watcher OnTextChanged: " + ch);
            communicationAsyncTask.execute(String.valueOf(ch));
        }

        @Override
        public void afterTextChanged(Editable s) {
            previousText = s.toString();
        }

        char getTypedCharacter(CharSequence currentText, CharSequence previousText) {
            int currentTextLength = currentText.length();
            int prevTextLength = previousText.length();
            Log.d("#darkfusion#", "PREV: " + previousText + " (n = " + prevTextLength + ")" + "\t\tCURRENT: " + currentText + " (n = " + currentTextLength + ")");

            if (currentTextLength >= prevTextLength) {
                Log.d("#darkfusion#", "MORE THAN: " + currentText.charAt(prevTextLength));
                return currentText.charAt(prevTextLength);
            } else {
                return BACKSPACE;
            }
        }
    };

    private KeyboardViewModel mViewModel;

    public static KeyboardFragment newInstance() {
        System.out.println("YAY NEW INSTANCE BEING CREATED!");
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("YAY ACTIVITY BEING CREATED!");
        mViewModel = ViewModelProviders.of(this).get(KeyboardViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        CommunicationAsyncTask communicationAsyncTask = new CommunicationAsyncTask(getContext());
        switch (v.getId()) {
            case R.id.escapeButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_ESCAPE));
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
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_NUM_LOCK));
                break;

            case R.id.capsLockButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_CAPS_LOCK));
                break;

            case R.id.scrollLockButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_SCROLL_LOCK));
                break;

            case R.id.functionKey_1:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F1));
                break;

            case R.id.functionKey_2:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F2));
                break;

            case R.id.functionKey_3:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F3));
                break;

            case R.id.functionKey_4:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F4));
                break;

            case R.id.functionKey_5:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F5));
                break;

            case R.id.functionKey_6:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F6));
                break;

            case R.id.functionKey_7:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F7));
                break;

            case R.id.functionKey_8:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F8));
                break;

            case R.id.functionKey_9:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F9));
                break;

            case R.id.functionKey_10:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F10));
                break;

            case R.id.functionKey_11:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F11));
                break;

            case R.id.functionKey_12:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_F12));
                break;

            case R.id.ctrlButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_CTRL_RIGHT));
                break;

            case R.id.altButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_ALT_RIGHT));
                break;

            case R.id.shiftButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_SHIFT_RIGHT));
                break;

            case R.id.windowsKey:
                communicationAsyncTask.execute(CustomKeyEvent.keyCodeToString(CustomKeyEvent.CUSTOM_KEYCODE_WINDOWS));
                break;

            case R.id.menuButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_MENU));
                break;
            case R.id.enterButton:
                communicationAsyncTask.execute(KeyEvent.keyCodeToString(KeyEvent.KEYCODE_ENTER));
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

    /**
     * Called from the onKeyDown() from the class MainActivity (overrides from AppCompatActivity)
     * Handles keyDown events (Mostly the backspace key in the soft keyboard)
     *
     * @param keyCode keyCode of the key that went down
     * @param context the context of the event
     */
    static void customOnKeyDown(int keyCode, Context context) {
        CommunicationAsyncTask communicationAsyncTask = new CommunicationAsyncTask(context);
        communicationAsyncTask.execute(KeyEvent.keyCodeToString(keyCode));
    }

    static class CommunicationAsyncTask extends AsyncTask<String, Void, Boolean> {
        WeakReference<Context> contextWeakReference;

        CommunicationAsyncTask(Context context) {
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(String... keyStrokes) {
            if (communicationHandler == null) {
                return false;
            }
            communicationHandler.sendKeyStroke(keyStrokes);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean taskSuccessful) {
            if (!taskSuccessful) {
                Context context = contextWeakReference.get().getApplicationContext();
                SingleToast.show(context,
                        context.getResources().getString(R.string.serverDisconnected),
                        Toast.LENGTH_LONG);
            }

            super.onPostExecute(taskSuccessful);
        }
    }

    void fetchAllButtonsFromLayout() {
        View rootView = getView();
        if (rootView == null) {
            Toast.makeText(getContext(), "Error launching the keyboard", Toast.LENGTH_LONG).show();
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

        editText = view.findViewById(R.id.keyboardEditText);
        editText.addTextChangedListener(textWatcher);
    }
}

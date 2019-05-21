package com.darkfusion.gaurav.noodledroid;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.darkfusion.gaurav.noodledroid.utils.Coordinate;
import com.darkfusion.gaurav.noodledroid.utils.SingleToast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.DEFAULT_KEYS_SEARCH_GLOBAL;
import static com.darkfusion.gaurav.noodledroid.MainActivity.communicationHandler;

public class TouchpadFragment extends Fragment implements View.OnTouchListener {
    static final int SCREEN_TOUCH_EVENT = 10;
    static final int BUTTON_EVENT = 20;
    static final int LEFT_BUTTON = 30;
    static final int RIGHT_BUTTON = 40;
    static final int MOUSE_MOVE = 50;
    static final int SCREEN_TAP = 60;
    static final int MAX_CLICK_DURATION = 200;
    static final int MAX_ACTIVE_POINTERS_ALLOWED = 2;
    static final int BUTTON_STATE_ACTIVE = 7;
    static final int BUTTON_STATE_INACTIVE = 8;

    private TouchpadViewModel mViewModel;

    private static Coordinate currentTouchCoordinate;
    private static Coordinate previousTouchCoordinate;

    static boolean isConnectedToServer;
    public static boolean isLeftButtonPressed;

    FrameLayout touchScreen;
    int activePointerIndex;
    int activePointerId;

    Map<Integer, PointF> activePointers;


    public static TouchpadFragment newInstance() {
        return new TouchpadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.touchpad_fragment, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        activePointers = new HashMap<>();

        View rootView = getView();

        if (rootView == null) {
            Toast.makeText(getContext(), "Unexpected error occured while loading TouchPad", Toast.LENGTH_LONG).show();
            super.onViewCreated(view, savedInstanceState);
            return;
        }

        Button leftButton = rootView.findViewById(R.id.touchpadLeftButton);
        leftButton.setOnTouchListener(this);

        Button rightButton = rootView.findViewById(R.id.touchpadRightButton);
        rightButton.setOnTouchListener(this);

        currentTouchCoordinate = new Coordinate(0, 0);
        previousTouchCoordinate = new Coordinate(0, 0);

        isConnectedToServer = isLeftButtonPressed = false;

        touchScreen = rootView.findViewById(R.id.touchpadScreen);
        touchScreen.setOnTouchListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TouchpadViewModel.class);
        // TODO: Use the ViewModel

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.touchpadScreen:
                handleScreenOnTouch(event);
                return true;
            case R.id.touchpadLeftButton:
                handleLeftButtonOnTouch(event, (Button) v);
                return true;
            case R.id.touchpadRightButton:
                handleRightButtonOnTouch(event, (Button) v);
                return true;
        }
        return false;
    }

    static class CommunicationAsyncTask extends AsyncTask<Integer, Void, Boolean> {
        MotionEvent event;
        WeakReference<Context> contextWeakReference;

        private CommunicationAsyncTask(Context context) {
            this.contextWeakReference = new WeakReference<>(context);
        }

        private void setEvent(MotionEvent event) {
            this.event = event;
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int eventCode = integers[0];
            int eventInfo = integers[1];

            switch (eventCode) {
                case SCREEN_TOUCH_EVENT:
                    return sendScreenTouchMessage(eventInfo);
                case BUTTON_EVENT:
                    return sendButtonEventMessage(eventInfo);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean taskSuccessful) {
            //if (!taskSuccessful) {
            //  showServerDisconnectedToast();
            //}

            super.onPostExecute(taskSuccessful);
        }

        private void showServerDisconnectedToast() {
            Context context = contextWeakReference.get().getApplicationContext();
            SingleToast.show(contextWeakReference.get().getApplicationContext(),
                    context.getResources().getString(R.string.serverDisconnected),
                    Toast.LENGTH_LONG);
        }

        private boolean sendScreenTouchMessage(int messageType) {
            if (communicationHandler == null) {
                communicationHandler = new CommunicationHandler(1);
                //return false;
            }

            switch (messageType) {
                case MOUSE_MOVE:
                    communicationHandler.sendMouseMove(calculateRelativeCoordinate());
                    break;
                case SCREEN_TAP:
                    communicationHandler.sendMouseClick();
                    break;
            }
            return true;
        }

        private boolean sendButtonEventMessage(int buttonType) {
            if (communicationHandler == null) {
                return false;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (buttonType == LEFT_BUTTON) {
                        communicationHandler.sendLeftButtonDown();
                    } else {
                        communicationHandler.sendRightButtonDown();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (buttonType == LEFT_BUTTON) {
                        communicationHandler.sendLeftButtonUp();
                    } else {
                        communicationHandler.sendRightButtonUp();
                    }
                    break;
            }

            return true;
        }

        private Coordinate calculateRelativeCoordinate() {
            return currentTouchCoordinate.subtract(previousTouchCoordinate);
        }
    }

    private void handleScreenOnTouch(MotionEvent event) {
        updateTouchCoordinates((int) event.getX(), (int) event.getY());

        CommunicationAsyncTask task = new CommunicationAsyncTask(getContext());
        task.setEvent(event);

        switch (event.getActionMasked()) {
            //A screen tap event
            case MotionEvent.ACTION_UP:
                if (isValidActionUpEvent(event)) {
                    Log.d("KIRAN", "ACTION_UP");
                    task.execute(SCREEN_TOUCH_EVENT, SCREEN_TAP);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isValidActionMoveEvent(event)) {
                    Log.d("KIRAN", "ACTION_MOVE");
                    task.execute(SCREEN_TOUCH_EVENT, MOUSE_MOVE);
                }
                break;
        }
    }

    private void handleLeftButtonOnTouch(MotionEvent event, Button button) {
        CommunicationAsyncTask task = new CommunicationAsyncTask(getContext());
        task.setEvent(event);

        int actionIndex = event.getActionIndex();
        int actionId = event.getPointerId(actionIndex);
        int pointerCount = Math.min(MAX_ACTIVE_POINTERS_ALLOWED, event.getPointerCount());

        switch (event.getActionMasked()) {
            //Ignores if the action_move happened on the active pointer (the one pressing the button)
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < pointerCount; i++) {
                    if (i == activePointerId) {
                        continue;
                    }
                    PointF point = activePointers.get(event.getPointerId(i));
                    if (point == null) {
                        continue;
                    }

                    int x = (int) event.getX(i);
                    int y = (int) event.getY(i);
                    updateTouchCoordinates(x, y);
                    task.execute(SCREEN_TOUCH_EVENT, MOUSE_MOVE);
                }
                break;

            case MotionEvent.ACTION_DOWN:
                activePointerIndex = actionIndex;
                activePointerId = actionId;

                PointF eventPoint = new PointF(event.getX(actionIndex), event.getY(actionIndex));
                activePointers.put(actionId, eventPoint);

                toggleButtonAppearance(button, BUTTON_STATE_ACTIVE);
                isLeftButtonPressed = true;
                task.execute(BUTTON_EVENT, LEFT_BUTTON);
                break;


            case MotionEvent.ACTION_POINTER_DOWN:
                PointF eventPoinst = new PointF(event.getX(actionIndex), event.getY(actionIndex));
                activePointers.put(actionId, eventPoinst);
                break;

            case MotionEvent.ACTION_UP:
                activePointers.remove(actionId);

                toggleButtonAppearance(button, BUTTON_STATE_INACTIVE);
                isLeftButtonPressed = false;
                task.execute(BUTTON_EVENT, LEFT_BUTTON);

        }
    }

    private void handleRightButtonOnTouch(MotionEvent event, Button button) {
        CommunicationAsyncTask task = new CommunicationAsyncTask(getContext());
        task.setEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                toggleButtonAppearance(button, BUTTON_STATE_ACTIVE);
                task.execute(BUTTON_EVENT, RIGHT_BUTTON);
                break;
            case MotionEvent.ACTION_UP:
                toggleButtonAppearance(button, BUTTON_STATE_INACTIVE);
                task.execute(BUTTON_EVENT, RIGHT_BUTTON);
        }
    }

    private void toggleButtonAppearance(Button button, int targetButtonState) {
        switch (targetButtonState) {
            case BUTTON_STATE_ACTIVE:
                button.setTextColor(getResources().getColor(R.color.buttonActiveColor));
                break;
            case BUTTON_STATE_INACTIVE:
                button.setTextColor(getResources().getColor(R.color.buttonInactiveColor));
                break;
        }
    }

    private boolean isValidActionMoveEvent(MotionEvent event) {
        int moveDistance = currentTouchCoordinate.eulerDistance(previousTouchCoordinate);
        long moveDuration = event.getEventTime() - event.getDownTime();

        return (moveDuration > MAX_CLICK_DURATION) && (moveDistance > 2);
    }

    private boolean isValidActionUpEvent(MotionEvent event) {
        int moveDistance = currentTouchCoordinate.eulerDistance(previousTouchCoordinate);

        long clickDuration = event.getEventTime() - event.getDownTime();
        return (clickDuration <= MAX_CLICK_DURATION) && (moveDistance < 2);
    }

    static void updateTouchCoordinates(int newX, int newY) {
        previousTouchCoordinate = currentTouchCoordinate.copy();
        currentTouchCoordinate.setCoordinate(newX, newY);
    }
}

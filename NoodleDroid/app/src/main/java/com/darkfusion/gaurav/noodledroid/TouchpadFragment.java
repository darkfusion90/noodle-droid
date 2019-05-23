package com.darkfusion.gaurav.noodledroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.darkfusion.gaurav.noodledroid.utils.Coordinate;
import com.darkfusion.gaurav.noodledroid.utils.SingleToast;

import java.lang.ref.WeakReference;

public class TouchpadFragment extends Fragment implements View.OnTouchListener {
    static final int SCREEN_TOUCH_EVENT = 10;
    static final int BUTTON_EVENT = 20;
    static final int LEFT_BUTTON = 30;
    static final int RIGHT_BUTTON = 40;
    static final int MOUSE_MOVE = 50;
    static final int SCREEN_TAP = 60;
    static final int MAX_CLICK_DURATION = 200;
    static final int MAX_ACTIVE_POINTERS_ALLOWED = 2;
    static final double MIN_MOVE_DISTANCE = 0.5;
    static final int BUTTON_STATE_ACTIVE = 7;
    static final int BUTTON_STATE_INACTIVE = 8;

    static boolean isConnectedToServer;
    public static boolean isLeftButtonPressed;
    private static Coordinate currentTouchCoordinate;
    private static Coordinate previousTouchCoordinate;

    FrameLayout touchScreen;
    int activePointerIndex;
    int activePointerId;
    SparseArray<PointF> activePointers;

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
        activePointers = new SparseArray<>();
        View rootView = getView();

        if (rootView == null) {
            SingleToast.show(getContext(), "Unexpected error occurred while loading TouchPad", Toast.LENGTH_LONG);
            onDestroy();
            return;
        }

        addButtonEventListeners(rootView);
        initializeTouchCoordinates();
        addTouchListenerToScreen(rootView);

        isConnectedToServer = isLeftButtonPressed = false;
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

    static class TouchpadCommunicationAsyncTask extends AsyncTask<Integer, Void, Boolean> {
        MotionEvent event;
        WeakReference<Context> contextWeakReference;

        private TouchpadCommunicationAsyncTask(Context context) {
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
            if (!taskSuccessful) {
                showServerDisconnectedToast();
            }

            super.onPostExecute(taskSuccessful);
        }

        private boolean sendScreenTouchMessage(int messageType) {
            if (MainActivity.communicationHandler == null) {
                return false;
            }

            switch (messageType) {
                case MOUSE_MOVE:
                    MainActivity.communicationHandler.sendMouseMove(calculateRelativeCoordinate());
                    break;
                case SCREEN_TAP:
                    MainActivity.communicationHandler.sendMouseClick();
                    break;
            }
            return true;
        }

        private boolean sendButtonEventMessage(int buttonType) {
            if (MainActivity.communicationHandler == null) {
                return false;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (buttonType == LEFT_BUTTON) {
                        MainActivity.communicationHandler.sendLeftButtonDown();
                    } else {
                        MainActivity.communicationHandler.sendRightButtonDown();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (buttonType == LEFT_BUTTON) {
                        MainActivity.communicationHandler.sendLeftButtonUp();
                    } else {
                        MainActivity.communicationHandler.sendRightButtonUp();
                    }
                    break;
            }

            return true;
        }

        private Coordinate calculateRelativeCoordinate() {
            return currentTouchCoordinate.subtract(previousTouchCoordinate);
        }

        private void showServerDisconnectedToast() {
            Context context = contextWeakReference.get().getApplicationContext();
            SingleToast.show(context,
                    context.getResources().getString(R.string.serverDisconnected),
                    Toast.LENGTH_LONG);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addButtonEventListeners(View rootView) {
        Button leftButton = rootView.findViewById(R.id.touchpadLeftButton);
        leftButton.setOnTouchListener(this);

        Button rightButton = rootView.findViewById(R.id.touchpadRightButton);
        rightButton.setOnTouchListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addTouchListenerToScreen(View rootView) {
        touchScreen = rootView.findViewById(R.id.touchpadScreen);
        touchScreen.setOnTouchListener(this);
    }

    private void initializeTouchCoordinates() {
        currentTouchCoordinate = new Coordinate(0, 0);
        previousTouchCoordinate = new Coordinate(0, 0);
    }

    private void sendMouseMoveMessage(MotionEvent event) {
        if (isValidMouseMoveEvent(event)) {
            TouchpadCommunicationAsyncTask communicationAsyncTask = new TouchpadCommunicationAsyncTask(getContext());
            communicationAsyncTask.setEvent(event);
            communicationAsyncTask.execute(SCREEN_TOUCH_EVENT, MOUSE_MOVE);
        }
    }

    private void sendMouseClickMessage(MotionEvent event) {
        if (isValidActionUpEvent(event)) {
            TouchpadCommunicationAsyncTask communicationAsyncTask = new TouchpadCommunicationAsyncTask(getContext());
            communicationAsyncTask.setEvent(event);
            communicationAsyncTask.execute(SCREEN_TOUCH_EVENT, SCREEN_TAP);
        }
    }

    private void handleScreenOnTouch(MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentTouchCoordinate.setCoordinate(X, Y);
                previousTouchCoordinate.setCoordinate(X, Y);
                break;
            case MotionEvent.ACTION_UP:
                sendMouseClickMessage(event);
                break;
            case MotionEvent.ACTION_MOVE:
                updateTouchCoordinates(X, Y);
                sendMouseMoveMessage(event);
                break;
        }
    }

    private void handleLeftButtonOnTouch(MotionEvent event, Button button) {
        TouchpadCommunicationAsyncTask task = new TouchpadCommunicationAsyncTask(getContext());
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
                    updateTouchCoordinates((int) event.getX(i), (int) event.getY(i));
                    sendMouseMoveMessage(event);
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
                final int X = (int) event.getX(1);
                final int Y = (int) event.getY(1);
                currentTouchCoordinate.setCoordinate(X, Y);
                previousTouchCoordinate.setCoordinate(X, Y);

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
        TouchpadCommunicationAsyncTask task = new TouchpadCommunicationAsyncTask(getContext());
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

    private boolean isValidMouseMoveEvent(MotionEvent event) {
        double moveDistance = currentTouchCoordinate.eulerDistance(previousTouchCoordinate);
        long moveDuration = event.getEventTime() - event.getDownTime();
        System.out.println("DISTANCE: " + moveDistance + " DURATION: " + moveDuration);

        return (moveDuration > MAX_CLICK_DURATION) && (moveDistance >= MIN_MOVE_DISTANCE);
    }

    private boolean isValidActionUpEvent(MotionEvent event) {
        double moveDistance = currentTouchCoordinate.eulerDistance(previousTouchCoordinate);

        long clickDuration = event.getEventTime() - event.getDownTime();
        return (clickDuration <= MAX_CLICK_DURATION) && (moveDistance < MIN_MOVE_DISTANCE);
    }

    static void updateTouchCoordinates(int newX, int newY) {
        previousTouchCoordinate = currentTouchCoordinate.copy();
        currentTouchCoordinate.setCoordinate(newX, newY);
    }
}

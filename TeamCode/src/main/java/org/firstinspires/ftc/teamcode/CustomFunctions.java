package org.firstinspires.ftc.teamcode;

public class CustomFunctions {
    boolean wasPressed = false, isPressed = false, output = false, downPosition = true;

    public boolean buttonDetection(boolean buttonState) {
        wasPressed = isPressed;
        isPressed = buttonState;

        if (isPressed && !wasPressed) {
            downPosition = !downPosition;

            if (downPosition) {
                output = true;
            } else {
                output = false;
            }
            return output;
        }
        return wasPressed;
    }
}
package org.firstinspires.ftc.teamcode;


public class TeleOp extends Robot {
        CustomFunctions customFunctions = new CustomFunctions();

    @Override
    public void init() {
        hardwareInit();
    }
    public void start() {

    }
    @Override
    public void loop() {
        if (customFunctions.buttonDetection(gamepad2.a)) {
            outtake(OuttakeState.deposit2);
        } else if (customFunctions.buttonDetection(gamepad2.b)) {
            outtake(OuttakeState.deposit1);
        } else if (customFunctions.buttonDetection(gamepad2.x)) {
            outtake(OuttakeState.intake);
        } else {
            outtake(OuttakeState.waitForState);
        }


    }

    @Override
    public void stop() {


    }


}

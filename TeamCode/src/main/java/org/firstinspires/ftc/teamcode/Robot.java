package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.Arrays;
import java.util.List;

@Config
public abstract class Robot extends OpMode {

    DcMotorEx leftSlide, rightSlide, intake;
    CRServo leftIntake, rightIntake, outtake;
    Servo armRotation, outtakeRotation;
    double currentThreshold = 8;

    double outtakeThreshold = 1;
    ElapsedTime outtakeTimer = new ElapsedTime();

    enum OuttakeState {
        intake, waitForState, deposit1, deposit2
    }

    public void hardwareInit() {
        List<LynxModule> allHubs;
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        List<DcMotor> slideMotors;
        List<CRServo> intakeServos;
        List<Servo> armRotationServos;

        leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        outtake = hardwareMap.get(CRServo.class, "outtake");

        armRotation = hardwareMap.get(Servo.class, "armRotation");
        outtakeRotation = hardwareMap.get(Servo.class, "outtakeRotation");

        slideMotors = Arrays.asList(leftSlide, rightSlide);

        for (DcMotor motor : slideMotors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntake.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void slides (int extensionDistance) {
        double spoolDiameter = 50; //Units in mm.
        double spoolCircumference = Math.PI * spoolDiameter;
        int MotorCountsPerRevolution = 28;
        double gearRatio = 2.89;
        double ShaftCountsPerRevolution = MotorCountsPerRevolution * gearRatio;
    }

    public void intake (boolean enabled) {
        int angularRate = 540;
        if (enabled) {
            intake.setVelocity(angularRate, AngleUnit.DEGREES);
            leftIntake.setPower(1);
            rightIntake.setPower(1);
            if (intake.getCurrent(CurrentUnit.AMPS) > currentThreshold) {
                intake.setVelocity(-angularRate); //Reverses the intake if it exceeds the current limit (Something is stuck).
            }
        } else {
            intake.setVelocity(0);
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }
    }

    public void outtake (OuttakeState outtakeState) {
        outtakeTimer.reset();

        switch (outtakeState) {
            case intake:
                while (outtakeTimer.seconds() > outtakeThreshold) {
                    outtake.setDirection(DcMotorSimple.Direction.FORWARD);
                    outtake.setPower(1);
                }
                break;

            case waitForState:
                outtake.setPower(0);
                break;

            case deposit1:
                while (outtakeTimer.seconds() > outtakeThreshold) {
                    outtake.setDirection(DcMotorSimple.Direction.REVERSE);
                    outtake.setPower(1);
                }
                break;

            case deposit2:
                while (outtakeTimer.seconds() > outtakeThreshold * 2) {
                    outtake.setDirection(DcMotorSimple.Direction.REVERSE);
                    outtake.setPower(1);
                }
                break;
        }
    }
}

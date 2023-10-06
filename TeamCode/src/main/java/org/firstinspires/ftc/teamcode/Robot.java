package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

public class Robot {
   public class hardwareInitialization {
      public DcMotor leftSlide;
      private List<DcMotor> slideMotors;

      leftSlide =hardwareMap.get(DcMotor .class,"leftSlide");
      rightSlide =hardwareMap.get(DcMotor .class,"rightslide";

      slideMotors =Arrays.asList(leftSlide,rightSlide);

   for())
   }
}

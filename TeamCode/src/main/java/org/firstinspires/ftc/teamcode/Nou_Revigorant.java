package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by adi on 2/27/2017.
 */

@TeleOp(name="Template: My OpMode", group="Linear Opmode")
public class Nou_Revigorant extends LinearOpMode {
    DcMotor motor_left = null;
    DcMotor motor_right = null;
    public Servo servo_left;
    public Servo servo_right;

    public static final double MID_SERVO       =  0.5 ;
    static final double MAX_POS     =  1.0;
    static final double MIN_POS     =  0.0;
    static final double INCREMENT   = 0.1;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   25;     // period of each cycle
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    double position = (MAX_POS - MIN_POS) / 2;
    boolean rampUp = true;


    public void startMotors()
    {
        motor_left  = hardwareMap.dcMotor.get("motor_stanga");
        motor_right = hardwareMap.dcMotor.get("motor_dreapta");

        {
            motor_left.setPower(0.4);
            motor_right.setPower(0.4);
        }
    }


    public void startServo()
    {
        servo_left = hardwareMap.servo.get("servo_stanga");
        servo_right = hardwareMap.servo.get("servo_dreapta");

            if (rampUp) {
                position += INCREMENT ;
                if (position >= MAX_POS ) {
                    position = MAX_POS;
                    rampUp = !rampUp;
                }
            }
            else {
                position -= INCREMENT ;
                if (position <= MIN_POS ) {
                    position = MIN_POS;
                    rampUp = !rampUp;
                }
            }

            /*telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update(); */

            servo_left.setPosition(position);
            servo_right.setPosition(position);
            sleep(CYCLE_MS);
            idle();

    }

    public void colorDetect() {
        ColorSensor colorSensor;
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);
        boolean bPrevState = false;
        boolean bCurrState = true;
        boolean bLedOn = true;
        colorSensor = hardwareMap.colorSensor.get("senzorCuloare");
        colorSensor.enableLed(bLedOn);

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        telemetry.addData("Clear", colorSensor.alpha() * 8);
        telemetry.addData("Red  ", colorSensor.red() * 8);
        telemetry.addData("Green", colorSensor.green() * 8);
        telemetry.addData("Blue ", colorSensor.blue()* 8);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        telemetry.update();
    }

    @Override
    public void runOpMode() {

        waitForStart();
        while(opModeIsActive()) {
            colorDetect();
        }
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Pushbot: Teleop",group="Pushbot")
public class TeleOP extends OpMode
{

    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor armMotor;
    Servo leftClaw;
    Servo rightClaw;

    //HardwareMap hwMap;

    //Gamepad gamepad1;

    float rightClawPos = 0.5f;
    float leftClawPos = 0.5f;

    //Servo final variables
    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    //float servoPos = 0.5;
    @Override
    public void init() //method sets initial motor configuration
    {
        //hwMap = ahwMap;
        //initialize wheels and arm to dc motors and claw to servos
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        armMotor = hardwareMap.dcMotor.get("left_arm");
        leftClaw = hardwareMap.servo.get("left_hand");
        rightClaw = hardwareMap.servo.get("right_hand");

        
        //set directions of the dc motors and position of claw
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        leftClaw.setPosition(0.5);
        rightClaw.setPosition(0.5);

        //Init power to motors to zero
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor.setPower(0);
    }

    //@Override
    public void loop() //method loops continuously
    {
        //gives val as to game pad joystick

        float leftY = -gamepad1.left_stick_y; //joysticks control individual wheels
        float rightY = -gamepad1.right_stick_y;
        boolean armUp = gamepad1.dpad_up; //the arrow pad controlls the arm, specifically up and down arrow
        boolean armDown = gamepad1.dpad_down;
        float clawLeftUp = gamepad1.left_trigger;//triggers make claw go outward
        float clawRightUp = gamepad1.right_trigger;
        boolean clawLeftDown = gamepad1.left_bumper;//bumpers make claw move inward
        boolean clawRightDown = gamepad1.right_bumper;
        int armVal = 0;
        if(armUp) //checks if the up is pressed and stores info in armVal
        {
            armVal = 1;
        }
        else if(armDown) //checks if down is pressed and stores in armVal
        {
            armVal = -1;
        }
        if(clawLeftUp == 1 && leftClawPos < 1.0) //checks if left trigger is pressed and increments position of left claw
        {
            leftClawPos += INCREMENT;
            rightClawPos +=INCREMENT;
        }
        if(clawRightUp == 1 && rightClawPos < 1.0)//checks if right trigger is pressed and increments position of right claw
        {
            rightClawPos +=INCREMENT;
            leftClawPos += INCREMENT;
        }
        if(clawLeftDown && leftClawPos > 0)//checks if left bumper is pressed and decreases position of left claw
        {
            leftClawPos -= INCREMENT;
            rightClawPos -= INCREMENT;
        }
        if(clawRightDown && rightClawPos > 0)//checks if right bumper is pressed and decreases position of right claw
        {
            rightClawPos -= INCREMENT;
            leftClawPos -= INCREMENT;
        }

        leftMotor.setPower(leftY); //sets power of wheel motors based on position of joysticks
        rightMotor.setPower(rightY);

        armMotor.setPower(armVal); //sets power of arm motor if arrow pad is being pressed

        leftClaw.setPosition(leftClawPos); //sets position of claw servo motor to variable leftClawPos
        rightClaw.setPosition(rightClawPos);


    }
}
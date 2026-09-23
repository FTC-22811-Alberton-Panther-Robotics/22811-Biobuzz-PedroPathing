package org.firstinspires.ftc.teamcode.InDevelopment;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotHardware.ActionManager;


@TeleOp(name = "BozemanTeleop", group = "01 Bozeman")
public class TeleopMode_1 extends OpMode {

    private ActionManager actionManager;

    public void init()
    {
        robot = new RobotHardwareContainer(hardwareMap, telemetry);


    }

    public void init_loop()
    {

    }

    public void start()
    {

    }

    public void loop()
    {


    }



    public double squareInputWithSign(double input) {

        return Math.copySign(input * input, input);
    }

}

package org.firstinspires.ftc.teamcode.InDevelopment;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotHardware.ActionManager;
import org.firstinspires.ftc.teamcode.RobotHardware.DriverAssist;
import org.firstinspires.ftc.teamcode.RobotHardware.RobotHardwareContainer;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@TeleOp(name = "BozemanTeleop", group = "01 Bozeman")
public class TeleopMode_1 extends OpMode {

    private RobotHardwareContainer robot;
    private ActionManager actionManager;
    private DriverAssist driverAssist;
    public static Pose startingPose;

    public void init()
    {
        robot = new RobotHardwareContainer(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap); // Pass it to the follower
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();
        driverAssist = new DriverAssist(follower);

        telemetry.addLine(" TeleOpMode_1 Initialized.");
        telemetry.update();

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

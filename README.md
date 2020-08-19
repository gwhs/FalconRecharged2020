# FalconRecharged2020

*Work in Progress*

## Software To Do List

  - Create a toRadians() method to take a degree input and returns the angle in Radians(for AutoPaths)
        -to be used in TrajectoryHelper.java

## General Information



## Autonomous Path Planning

  ###### How to create a new Autonomous Path
    
    1. Create a New AutoPath in [java \ frc \ robot \ commands \ AutoPaths]
            1. Right Click "AutoPaths" on the left explorer bar
            2. Click Create a new Class/Command
            3. Type in and/or select "SequentialCommandGroup (New)"
            4. Enter a name for your new AutoPath
            5. Replace the reference to the super consutructor( super(); ) with addCommands();
                1. This allows you to add many commands for the Robot to follow,
                  including many sequential AutoPaths or tasks such as 
                  picking up power cells, etc...
                2. The change from super(); to addCommands(); was decided after
                  the Autonomous Software Group ran in to various problems with super();
    2. Go to TrajectoryHelper.java to set up a new TrajectoryMaker
            1. *note* Each TrajectoryMaker has a Trajectory Instance Variable that can get accessed
              using the method getTrajectory()
            2. As implied by the name TrajectoryMaker, the class makes the trajectory for you
            3. Set up a new Trajectory Maker using the following format:
            
                    public static TrajectoryMaker trajMakerName()
                    {
                        //Add ArrayList and points here if necessary
                        return new TrajectoryMaker(*look at the following steps to pick a constructor);
                    }
                    
            - There are 2 Types of TrajectoryMakers(2 different constructors)
                1. The First type of TrajectoryMaker allows the Robot to move a distance in any direction.
                
                        public TrajectoryMaker(Pose2d start, Pose2d end, boolean isHyp)
                        
                    - Moves in a straight line from starting point to end point
                    - uses Pose2d start, Pose2d end, boolean isHyp as inputs
                    - If using this constructor, set isHyp to true, setting it to false may not move 
                      the robot or may produce unknown results.
                2. The Second type of TrajectoryMaker uses the SwerveDrives as if it was 
                   a TankDrive(as if the wheels cannot rotate)
                
                        public TrajectoryMaker(Pose2d start, Pose2d end, ArrayList<Translation2d> points)
                        
                    - Moves from start to end point without rotating wheels
                    - uses Pose2d start, Pose2d end, and an ArrayList of Translation2d Objects as points
                    - does not require isHyp input because it is set to false by default for this constructor.
                    - add points to an ArrayList using:
                            points.add(new Translation2d(x, y));
                        - x and y should be in METERS
                    - add points before returning the TrajectoryMaker
            - Start and End Points are Pose2d Objects
                - Pose2d Objects can be created with the following inputs:
                        new Pose2d(double x, double y, Rotation2d rotation)
                - the x and y inputs for Pose2d need to be entered in METERS
                - Angle is inputed using a Rotation2d object, which can be created using:
                        new Rotation2d(angleInRadians)
    3.
                



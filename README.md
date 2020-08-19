# FalconRecharged2020

*Work in Progress*

---General Information



---Autonomous Path Planning

  - How to create a new Autonomous Path
    
    1. Create a New AutoPath in [java \ frc \ robot \ commands \ AutoPaths]
            - Right Click "AutoPaths" on the left explorer bar
            - Click Create a new Class/Command
            - Type in and/or select "SequentialCommandGroup (New)"
            - Enter a name for your new AutoPath
            - Replace the reference to the super consutructor( super(); ) with addCommands();
                - This allows you to add many commands for the Robot to follow,
                  including many sequential AutoPaths or tasks such as 
                  picking up power cells, etc...
                - The change from super(); to addCommands(); was decided after
                  the Autonomous Software Group ran in to various problems with super();
    2. Go to TrajectoryHelper.java to set up a new TrajectoryMaker
            - *note* Each TrajectoryMaker has a Trajectory Instance Variable that can get accessed
              using the method getTrajectory()
            - As implied by the name TrajectoryMaker, the class makes the trajectory for you
            - There are 2 Types of TrajectoryMakers(2 different constructors)
                1. The First type of TrajectoryMaker allows the Robot to move a distance in any direction.
                    - Moves in a straight line from starting point to end point
                    - uses Pose2d start, Pose2d end, boolean isHyp as inputs
                    - If using this constructor, set isHyp to true, setting it to false may not move the robot or may produce unknown results.
                2. The Second type of TrajectoryMaker uses the SwerveDrives as if it was a TankDrive(as if the wheels cannot rotate)
                    - Moves from start to end point without rotating wheels
                    - uses Pose2d start, Pose2d end, and an ArrayList of Translation2d Objects as points
                    - does not require isHyp input because it is set to false by default for this constructor.

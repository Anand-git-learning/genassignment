# Gen Interview Assignment

Program that downloads a catalog of exoplanet data and displays required information

## About the project
Program that downloads a catalog of exoplanet data and displays the following information:

`The number of orphan planets (no star).`
`The name (planet identifier) of the planet orbiting the hottest star.`
`A timeline of the number of planets discovered per year grouped by size. Use the following groups: “small” is less than 1 Jupiter radii, “medium” is less than 2 Jupiter radii, and anything bigger is considered “large”. For example, in 2004 we discovered 2 small planets, 5 medium planets, and 0 large planets.`

The dataset can be found in JSON format here: https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets

And is documented here: https://www.kaggle.com/mrisdal/open-exoplanet-catalogue

## Instructions for execution of program
Requirements
JDK (Java Development Kit) version 8 or higher
Maven build tool

## Running the Program
Clone the repository to your local machine.
Navigate to the project directory in the command line.
Compile the Java source files by running mvn compile.
Run the program by executing the main class. For example, if the main class is com.example.Main, run java com.example.Main.

## Running the Tests
Navigate to the project directory in the command line.
Run the tests by executing mvn test.
View the test results in the command line output. If all tests pass, you should see a message indicating that all tests were successful.

## Additional Notes
If the program or tests require any external dependencies, make sure to include them in the pom.xml file and run mvn compile or mvn test to download and include them in the project.
If you encounter any issues running the program or tests, make sure to check the console output for any error messages that may provide additional context.

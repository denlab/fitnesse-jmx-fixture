# fitnesse-jmx-fixture

## DESCRIPTION:

Fixture to call a JMX MBean operation using RMI from a [FitNesse](http://fitnesse.org/) page. 

## HOW-TO:

To use this Fixture you must add the jar file of the project containing this class in the lib directory of FitNesse. 

To generate the jar file from the project, you can use this command: `mvn clean install`. Then you will find the jar file in the target directory. 

Here a sample code to use it in a FitNesse page: 

    !contents 
      
    !path /home/ec2-user/fitnesse/fitnesse.jar 
    !path /home/ec2-user/fitnesse/lib/* 
      
    !|ActionFixture| 
    |start|com.adeo.fitnesse.JMXFixture| 
    |enter|setHostport|localhost:10102| 
    |enter|setOperationName|resetLilyState| 
    |enter|setObjectName|LilyLauncher:name=Launcher| 
    |check|callJMXOperation|true| 

## Happy acceptance tests !!!

## License

[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)
  

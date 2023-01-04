run:Application

Application: Application.class
	java Application.java

Application.class:
	javac Application.java

runTests:runBackendDeveloperTests runDataWranglerTests runAlgorithmEngineerTests runFrontendDeveloperTest

runFrontendDeveloperTest: FrontendDeveloperTest.class
	javac -cp .:junit5.jar FrontendDeveloperTest.java
runBackendDeveloperTests: BackendDeveloperTest.class
	java -jar junit5.jar --class-path=. --include-classname=.* --select-class=BackendDeveloperTest

BackendDeveloperTest.class: BackendDeveloperTest.java
	javac -cp .:junit5.jar BackendDeveloperTest.java

runDataWranglerTests:DataWranglerTest

DataWranglerTest:
	javac -cp .:junit5.jar DataWranglerTest.java
	java -jar junit5.jar -cp . --scan-classpath
ProduceLoader:
	javac -cp .:dom-2.3.0-jaxb-1.0.6.jar ProduceLoader.java
	java -cp .:dom-2.3.0-jaxb-1.0.6.jar ProduceLoader
clean:
	rm *.class

runAlgorithmEngineerTests: AlgorithmEngineerTests.class
	java -jar junit5.jar --class-path=. --include-classname=.* --select-class=AlgorithmEngineerTests

AlgorithmEngineerTests.class:
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

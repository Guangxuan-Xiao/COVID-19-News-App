javac -classpath gson-2.8.6.jar RegionParser.java Region.java
java -classpath .:gson-2.8.6.jar RegionParser < epidemic.json > region_out.txt
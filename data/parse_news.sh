javac -classpath gson-2.8.6.jar NewsParser.java News.java
java -classpath .:gson-2.8.6.jar NewsParser > news_out.txt
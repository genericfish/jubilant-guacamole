mkdir -p ./bin
javac *.java -d ./bin
java -cp "./bin:postgresql-42.2.8.jar" TheSQL

This is a simple Socket manager
Java Programming Language based on JVM>=1.8

Description:
this is provides a serverside Socket and clientside Secket
for communicate between client
server handles this communication
this is based on Commandline interface
but can be easily implemented on All Java Supported platform and Dalvik machine(android)

Setup:
in cmd/terminal type the following code
java -jar App/Server.jar
this is create a socket connection that listens to port 4545 for incomming connections

open  another terminal/cmd enter following code
java -jar App/Client.jar
use this command to register yourself to server
/register amir
you can open multiple terminals or cmd in this server or somewhere in the world and do it as the same
for example 
/register ali
and enter this to see online users
/onlines 
and start a chat with any body that you want
/chat amir
other client must accept your chat request by
/accept ali
when this hapens convesation is begin

SourceCode and Build:
all this source code is under SocketTest folder
SocketTest/Client
and 
SocketApp/Server
that can be builded with gradle build system with the following code in terminal
cd SocketApp
for build Client Executable java archive
./gradlew Client:jar
and for build Server Executable java archive
./gradlew Server:jar
files go under {module_name}build/libs/{module_name}.jar
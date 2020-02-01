Java Programming Language based on JVM>=1.8
This is a simple Socket manager for see how sockets works in java

Description:
    this is provides a serverside Socket and clientside Secket
    for communicate between client
    server handles this communication
    this is based on Commandline interface
    but can be easily implemented on All Java Supported platform and Dalvik machine(android)



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


Setup:
    in build section collect Server.jar and Client.jar file from output dir

Setup server:
    then in cmd/terminal type the following code
    java -jar path/to/Server.jar
    this is create a socket connection that listens to port 4545 for incomming connections

Setup Client:
    open another terminal/cmd enter following code
    java -jar path/to/Client.jar
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
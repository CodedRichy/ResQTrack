@echo off
echo Building ResQTrack Disaster Management System...

echo.
echo Compiling Java files...
javac -cp ".;h2-2.2.224.jar" -d . database/*.java
javac -cp ".;h2-2.2.224.jar" -d . service/*.java
javac -cp ".;h2-2.2.224.jar" -d . userinterface/*.java
javac -cp ".;h2-2.2.224.jar" -d . main/*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Build completed successfully!
echo.
echo To run the application:
echo java -cp ".;mysql-connector-j-9.4.0.jar" main.Main
echo.
pause

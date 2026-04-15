@echo off
echo ==========================================
echo   Food Delivery System - Compile and Run
echo ==========================================
echo.

REM Create output directory
if not exist out mkdir out

REM Compile all Java files
echo Compiling Java files...
javac -cp "lib/*" -d out src\model\*.java src\db\*.java src\dao\*.java src\Main.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo COMPILATION FAILED! Please check errors above.
    echo Make sure JDK is installed and sqlite-jdbc JAR is in lib\ folder.
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting Food Delivery System...
echo.
java -cp "out;lib/*" Main

pause

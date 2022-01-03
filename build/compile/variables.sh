#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
# Bash colors using ANSI 
# favored the 24-bit (3 colors * 8-bit for each one<255>) color system
# Colors
# process failure color
RED_C='\033[38;2;255;50;50m'
# success color
GREEN_C='\e[38;2;0;180;0m'
# extra
WHITE_C='\e[38;2;255;255;255m'
# Java color
ORANGE_C='\e[38;2;250;155;0m'
# C alternative color
DARK_GREY_C='\e[1;30m'
# kotlin color
VIOLET_C='\e[38;2;217;80;223m'
# groovy color
CYAN_C='\e[38;2;0;155;255m'
# C++ color
MAGNETA_C='\e[38;2;170;150;150m'
# Scala color
BRIGHT_RED_C='\e[38;2;200;120;120m'
# Highlights
RED_H='\033[1;41m'
GREEN_H='\033[1;42m'
WHITE_H='\e[1;47m'
# Flashes
RED_C_F='\033[1;5;41m'
GREEN_C_F='\033[1;5;42m'
WHITE_C_F='\e[1;5;47m'
RESET_Cs='\033[0;0m'


# define work directory
# 1) print the current working directory to a string value
pwd=`pwd`
# cut the working directory from its end by a one '/' delimiter
cut="${pwd%/*}"
# cut the working directory from its end by a one '/' delimiter again
cut2="${cut%/*}"
# pass the value of the dire
workDir=$cut2
# include the JAVAHOME
source ${workDir}'/JAVAHOME.sh'
command=${JAVA__HOME}'/javac'
clibName=('libArithmosNatives.so')
# set some build guards
enable_java_build=true
enable_scala_build=true
enable_kt_build=true
enable_groovy_build=true
enable_natives_build=true
enable_android_build=true

source ${workDir}'/NDKPATH.sh'

min_android_sdk=21
arm64="aarch64-linux-android"
arm64_lib="arm64-v8a"
arm32="armv7a-linux-androideabi"
arm32_lib="armeabi-v7a"
intel32="i686-linux-android"
intel32_lib="x86"
intel64="x86_64-linux-android"
intel64_lib="x86_64"

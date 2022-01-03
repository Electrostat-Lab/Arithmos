#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
# Bash colors using ANSI 

# Colors
RED_C='\033[1;31m'
GREEN_C='\033[1;32m'
WHITE_C='\e[1;37m'
# Highlights
RED_H='\033[1;41m'
GREEN_H='\033[1;42m'
WHITE_H='\e[1;47m'
# Flashes
RED_C_F='\033[1;5;41m'
GREEN_C_F='\033[1;5;42m'
WHITE_C_F='\e[1;5;47m'


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

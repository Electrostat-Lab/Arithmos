#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#


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
enable_scala_build=false
enable_kt_build=true
enable_groovy_build=false
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

# Common Variables contain colors 
source ${workDir}'/CommonVariables.sh'

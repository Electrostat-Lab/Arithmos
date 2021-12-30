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
enable_scala_build=true
enable_kt_build=true
enable_groovy_build=true
enable_natives_build=false
enable_android_build=true

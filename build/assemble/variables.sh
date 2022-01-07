#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# print the current working directory to a string value
pwd=`pwd`
# cut the working directory from its end by a one '/' delimiter
cut="${pwd%/*}"
# cut the working directory from its end by a one '/' delimiter again
cut2="${cut%/*}"
# pass the value of the dire
workingDir=$cut2
# include the Ccoffee JAVAHOME file
source ${workingDir}'/JAVAHOME.sh'
source ${cut}'/compile/variables.sh'
outputJAR='Arithmos'
manifest='Manifest-Version: 1.0'
mainclass='Main-Class: main.TestCase'
command=${JAVA__HOME}'/jar'
classpath='Class-Path: dependencies'

# CommonVariables script contains colors 
source ${workDir}'/CommonVariables.sh'

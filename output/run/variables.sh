#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

# 1) print the current working directory to a string value
pwd=`pwd`
# cut the working directory from its end by a one '/' delimiter
cut="${pwd%/*}"
# cut the working directory from its end by a one '/' delimiter again
cut2="${cut%/*}"
# pass the value of the dire
workingDir=$cut2
# include the JAVAHOME
source ${workingDir}'/JAVAHOME.sh'
runCommand=${JAVA__HOME}'/java'

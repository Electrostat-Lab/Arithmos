#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
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
# include the Ccoffee JAVAHOME file
source ${workingDir}'/JAVAHOME.sh'
source ${cut}'/compile/variables.sh'
outputJAR='Arithmos'
manifest='Manifest-Version: 1.0'
mainclass='Main-Class: main.TestCase'
command=${JAVA__HOME}'/jar'
classpath='Class-Path: dependencies'

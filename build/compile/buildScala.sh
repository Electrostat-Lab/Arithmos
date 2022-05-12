#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh

##
# Compiles and package Scala into a dependency jar file to be included inside the java module.
# @return compilation result.
##
function compileScala() {
   local result=-1
   scala_sources=`find $scalasrc_directory -name '*.scala'`
   if [[ -f $scala_sources ]]; then
         cd $dependencies
         scalac $scala_sources -d $scala_jar
         result=$?
   fi
   return $result
}


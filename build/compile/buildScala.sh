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
   local compileResult=-1
   scalaFiles=`find $scalasrc_directory -name '*.scala'`
   dependencies=$java_resources'/dependencies'
   if [[ -f ${scalaFiles} ]]; then
         scalac ${scalaFiles} -d $java_resources'/dependencies/scala.jar'
         compileResult=$?
   fi
   return $compileResult
}


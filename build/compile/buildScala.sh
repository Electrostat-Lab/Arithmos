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
   scalaFiles=`find ${workDir}'/code/scala/src' -name '*.scala'`
   dependencies=${workDir}'/code/java/dependencies'
   if [[ -f ${scalaFiles} ]]; then
         scalac ${scalaFiles} -d ${workDir}'/code/java/dependencies/scala.jar'
         compileResult=$?
   fi
   return $compileResult
}


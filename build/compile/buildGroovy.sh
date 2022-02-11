#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh

##
# Compiles and package groovy into a dependency jar file to be included inside the java module.
# @return Compilation result, 0 for success.
##
function compileGroovy() {
   local compileResult=-1
   groovyFiles=`find ${workDir}'/code/groovy/src' -name '*.groovy'`
   # compile groovy sources if exist
   if [[ -f ${groovyFiles} ]]; then
       groovyc ${groovyFiles} -d ${workDir}'/code/java/dependencies/groovy'
	   cd ${workDir}'/code/java/dependencies/groovy'
       zip  -r groovy.jar . -i '*/*'
       cp groovy.jar ${workDir}'/code/java/dependencies'
       compileResult=$?
        # remove the assets folder
       rm -rf ${workDir}'/code/java/dependencies/groovy'
   fi
   return $compileResult
}

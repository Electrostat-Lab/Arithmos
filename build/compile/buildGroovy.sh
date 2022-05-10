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
   groovyFiles=`find $groovysrc_directory -name '*.groovy'`
   # compile groovy sources if exist
   if [[ -f ${groovyFiles} ]]; then
       groovyc ${groovyFiles} -d $java_resources'/dependencies/groovy'
	   cd $java_resources'/dependencies/groovy'
       zip  -r groovy.jar . -i '*/*'
       cp groovy.jar $java_resources'/dependencies'
       compileResult=$?
        # remove the assets folder
       rm -rf $java_resources'/dependencies/groovy'
   fi
   return $compileResult
}

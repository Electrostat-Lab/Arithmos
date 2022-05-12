#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh

##
# Compiles and package groovy into a dependency jar file to be included inside the java module.
#
# @return Compilation result, 0 for success.
##
function compileGroovy() {
   local result=-1
   groovy_files=`find $groovysrc_directory -name '*.groovy'`
   # compile groovy sources if exist
   if [[ -f $groovy_files ]]; then
       groovyc $groovy_files -cp '.:'$dependencies -d $groovy_tmp
	   cd $groovy_tmp
       zip -r $groovy_jar . -i '*/*'
       cp $groovy_jar $dependencies
       result=$?
        # remove the assets folder
       rm -rf $groovy_tmp
   fi
   return $result
}

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh

##
# Compiles and package kotlin into a dependency jar file to be included inside the java module.
# @return Compilation result, 0 for success.
##
function compileKotlin() {
   local compileResult=-1
   ktFiles=`find $kotlinsrc_directory -name '*.kt'`
   if [[ -f ${ktFiles} ]]; then
        dependencies=$java_resources'/dependencies'

        if [[ $enable_android_build == true ]]; then
            # Compile java and kotlin sources together
            kotlinc-jvm -cp $dependencies $ktFiles $javasrc_directory $scalasrc_directory $groovysrc_directory -d $java_resources'/dependencies/kotlin.jar'
        else
            kotlinc-jvm -cp $dependencies $ktFiles $javasrc_directory $scalasrc_directory $groovysrc_directory -include-runtime -d $java_resources'/dependencies/kotlin.jar'
        fi
        compileResult=$?
   fi
   return $compileResult
}


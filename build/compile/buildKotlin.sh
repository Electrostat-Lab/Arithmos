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
   ktFiles=`find ${workDir}'/code/kotlin/src' -name '*.kt'`
   if [[ -f ${ktFiles} ]]; then
        dependencies=${workDir}'/code/java/dependencies'
        javaSources=${workDir}'/code/java/src'
	    scalaSources=${workDir}'/code/scala/src'
	    groovySources=${workDir}'/code/groovy/src'
        if [[ $enable_android_build == true ]]; then
            # Compile java and kotlin sources together
            kotlinc-jvm -cp $dependencies $ktFiles $javaSources $scalaSources $groovySources -d ${workDir}'/code/java/dependencies/kotlin.jar'
        else
            kotlinc-jvm -cp $dependencies $ktFiles $javaSources $scalaSources $groovySources -include-runtime -d ${workDir}'/code/java/dependencies/kotlin.jar'
        fi
        compileResult=$?
   fi
   return $compileResult
}


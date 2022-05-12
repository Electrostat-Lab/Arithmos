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
   local result=-1
   kt_sources=`find $kotlinsrc_directory -name '*.kt'`
   if [[ -f $kt_sources ]]; then
        cd $dependencies
        if [[ $enable_android_build == true ]]; then
            # Compile java and kotlin sources together
            kotlinc-jvm -cp $dependencies $kt_sources $javasrc_directory $scalasrc_directory $groovysrc_directory -d $kotlin_jar
        else
            kotlinc-jvm -cp $dependencies $kt_sources $javasrc_directory $scalasrc_directory $groovysrc_directory -include-runtime -d $kotlin_jar
        fi
        result=$?
   fi
   return $result
}


#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for kotlin byte code
if [[ ! -d ${workDir}'/build/.buildKotlin' ]]; then
    mkdir ${workDir}'/build/.buildKotlin'
fi
##
# Copies the source files to a single dir to be compiled manually.
##
function copyKtSources() {
    #copy code to buildDir to compile java files
    codeFiles=(${workDir}'/code/kotlin/src/*')
    if [[ ${codeFiles} ]]; then
        cp -r ${codeFiles} ${workDir}'/build/.buildKotlin'
    fi
}
##
# Compiles and package kotlin into a dependency jar file to be included inside the java module.
# @return Compilation result, 0 for success.
##
function compileKotlin() {
   local compileResult=-1
   cd ${workDir}'/build/.buildKotlin'
   ktFiles=`find -name '*.kt'`
   if [[ -f ${ktFiles} ]]; then
        if [[ $enable_android_build == true ]]; then
            kotlinc ${ktFiles} -d ${workDir}'/code/java/dependencies/kotlin.jar'
        else
            kotlinc ${ktFiles} -include-runtime -d ${workDir}'/code/java/dependencies/kotlin.jar'
        fi
        compileResult=$?
        ## remove sources after compilation is completed
        rm -r $ktFiles
   fi
   return $compileResult
}


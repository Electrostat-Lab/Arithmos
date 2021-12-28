#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
if [[ ! -d ${workDir}'/build/.buildKotlin' ]]; then
    mkdir ${workDir}'/build/.buildKotlin'
fi
##
# Copies the source files to a single dir to be compiled manually.
##
function copyKtSources() {
    #copy code to buildDir to compile java files
    codeFiles=(${workDir}'/code/kotlin/src/*')
    if [[ -f ${codeFiles} ]]; then
        cp -r ${codeFiles} ${workDir}'/build/.buildKotlin'
    fi
}
##
# Compiles and package kotlin into a dependency jar file to be included inside the java module.
##
function compileKotlin() {
   cd ${workDir}'/build/.buildKotlin'
   ktFiles=`find -name '*.kt'`
   if [[ -f ${ktFiles} ]]; then
        kotlinc ${ktFiles} -include-runtime -d ${workDir}'/code/java/dependencies/kotlin.jar'
        ## remove sources after compilation is completed
        rm -r $ktFiles
   fi
}


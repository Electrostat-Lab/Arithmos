#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
if [[ ! -d ${workDir}'/build/.buildScala' ]]; then
    mkdir ${workDir}'/build/.buildScala'
fi
##
# Copies the source files to a single dir to be compiled manually.
##
function copyScSources() {
    #copy code to buildDir to compile java files
    codeFiles=(${workDir}'/code/scala/src/*')
    if [[ -f ${codeFiles} ]]; then
        cp -r ${codeFiles} ${workDir}'/build/.buildScala'
    fi
}
##
# Compiles and package Scala into a dependency jar file to be included inside the java module.
##
function compileScala() {
   cd ${workDir}'/build/.buildScala'
   scalaFiles=`find -name '*.scala'`
   if [[ -f ${scalaFiles} ]]; then
         scalac ${scalaFiles} -d ${workDir}'/code/java/dependencies/scala.jar'
         ## remove sources after compilation is completed
         rm -r $scalaFiles
   fi
}


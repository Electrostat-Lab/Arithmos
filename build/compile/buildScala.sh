#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
mkdir ${workDir}'/build/.buildScala'

##
# Copies the source files to a single dir to be compiled manually.
##
function copyScSources() {
    #copy code to buildDir to compile java files
    codeDir=(${workDir}'/code/scala/src/*')
    cp -r ${codeDir} ${workDir}'/build/.buildScala'
}
##
# Compiles and package Scala into a dependency jar file to be included inside the java module.
##
function compileScala() {
   buildDir=(${workDir}'/build/.buildScala/*/*.scala')
   cd ${workDir}'/build/.buildScala'
   scalac ${buildDir} -d ${workDir}'/code/java/dependencies/scala.jar'
   ## remove sources after compilation is completed
   rm -r $buildDir
}


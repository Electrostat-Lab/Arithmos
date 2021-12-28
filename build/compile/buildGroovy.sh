#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
if [[ ! -d ${workDir}'/build/.buildGroovy' ]]; then
    mkdir ${workDir}'/build/.buildGroovy'
fi
##
# Copies the source files to a single dir to be compiled manually.
##
function copyGroovySources() {
    #copy code to buildDir to compile java files
    codeDir=(${workDir}'/code/groovy/src/*')
    if [[ -d ${codeDir} ]]; then
        cp -r ${codeDir} ${workDir}'/build/.buildGroovy'
    fi
}
##
# Compiles and package kotlin into a dependency jar file to be included inside the java module.
##
function compileGroovy() {
   cd ${workDir}'/build/.buildGroovy'
   groovyFiles=`find -name '*.groovy'`
   # compile groovy sources if exist
   if [[ -f ${groovyFiles} ]]; then
       groovyc ${groovyFiles} -d ${workDir}'/code/java/dependencies/groovy'
       cd ${workDir}'/code/java/dependencies/groovy'
       zip -r groovy.jar . -i '*/*'
       cp groovy.jar ${workDir}'/code/java/dependencies/groovy' ${workDir}'/code/java/dependencies'
        # remove the assets folder
       rm -rf ${workDir}'/code/java/dependencies/groovy'
       ## remove sources after compilation is completed
       cd ${workDir}'/build/.buildGroovy' 
       rm -r $groovyFiles  
   fi
}

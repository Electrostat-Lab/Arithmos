#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
mkdir ${workDir}'/build/.buildGroovy'

##
# Copies the source files to a single dir to be compiled manually.
##
function copyGroovySources() {
    #copy code to buildDir to compile java files
    codeDir=(${workDir}'/code/groovy/src/*')
    cp -r ${codeDir} ${workDir}'/build/.buildGroovy'
}
##
# Compiles and package kotlin into a dependency jar file to be included inside the java module.
##
function compileGroovy() {
   buildDir=(${workDir}'/build/.buildGroovy/*/*.groovy')
   cd ${workDir}'/build/.buildGroovy'
   groovyc ${buildDir} -d ${workDir}'/code/java/dependencies/groovy'
   cd ${workDir}'/code/java/dependencies/groovy'
   zip -r groovy.jar . -i '*/*'
   cp groovy.jar ${workDir}'/code/java/dependencies/groovy' ${workDir}'/code/java/dependencies'
    # remove the assets folder
   rm -rf ${workDir}'/code/java/dependencies/groovy'
   ## remove sources after compilation is completed
   rm -r $buildDir
}


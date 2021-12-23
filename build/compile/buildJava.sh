#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
mkdir ${workDir}'/build/.buildJava'

##
# Copies the source files to a single dir to be compiled manually.
##
function copyJavaSources() {
    #copy code to buildDir to compile java files
    codeDir=(${workDir}'/code/java/src/*')
    cp -r ${codeDir} ${workDir}'/build/.buildJava'
}

##
# Generates C headers for the java native files and compiles java code inside the buildDir.
##
function generateHeaders() {
    buildDir=(${workDir}'/build/.buildJava/*/*.java')
    cd ${workDir}'/build/.buildJava'
    # creates C headers file for java natives.
    $command -cp '.:'${workDir}'/code/java/dependencies/*' -h . ${buildDir}
    # generate a methods signature file to help in invocation api
    classFiles=(${workDir}'/build/.buildJava/*/*.class')
    sigs=`javap -s -p ${classFiles}`
    printf ' %s \n' ${sigs} > 'sig.signature'
    # remove the source code
    rm $buildDir
}

##
# Moves all the generated header files from the build dir to the include dir of the natives.
##
function moveHeaders() {
    headers=(${workDir}'/build/.buildJava/*.h')
    mv ${headers} ${workDir}'/code/natives/includes/'
}


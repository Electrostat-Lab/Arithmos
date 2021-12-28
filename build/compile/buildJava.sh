#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#
source variables.sh
# make a dir for java byte code
if [[ ! -d ${workDir}'/build/.buildJava' ]]; then
    mkdir ${workDir}'/build/.buildJava'
fi

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
    buildDir=${workDir}'/build/.buildJava'
    cd ${buildDir}
    javaFiles=`find -name "*.java"`
    # creates C headers file for java natives.
    $command -cp '.:'${workDir}'/code/java/dependencies/*' -h . $javaFiles
    # generate a methods signature file to help in invocation api
    classFiles=`find -name "*.class"`
    sigs=`javap -s -p ${classFiles}`
    printf ' %s \n' ${sigs} > 'sig.signature'
    # remove the source code
    rm $javaFiles
}

##
# Moves all the generated header files from the build dir to the include dir of the natives.
##
function moveHeaders() {
    headers=(${workDir}'/build/.buildJava/*.h')
    # check if the headers exist then move them
    if [[ -f $headers ]]; then
        mv ${headers} ${workDir}'/code/natives/includes/'
    fi
}


#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*#
source variables.sh
# Compile C++ code & bind to the header created from java
mkdir ${workDir}'/build/.buildNatives'

function copyNativeSources() {
     # dir to compile & sharedLib name
    libs=(${workDir}'/code/natives/libs/*')
    main=(${workDir}'/code/natives/main/*')
    # copy cpp files to a gather directory
    cp -r ${libs} ${workDir}'/build/.buildNatives'
    cp -r ${main} ${workDir}'/build/.buildNatives'
}

function compile() {
    cd ${workDir}'/build/.buildNatives'
    nativeSources=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    chmod +x $nativeSources
    # append -lwiringPi for raspberry wiringPi includes
    # ${JAVA__HOME%/*} : % returns back to the root base directory of the java home, / is the separator delimiter of the directory string
    g++ -fPIC ${nativeSources} -shared -o ${clibName} \
                -I${JAVA__HOME%/*}'/include' \
                -I${JAVA__HOME%/*}'/include/linux' \
                -I${workDir}'/code/natives/includes' 
    mv ${clibName} ${workDir}'/shared'
    rm $nativeSources
} 

function setJavaLibSource() {
    java.library.path=${workDir}'/shared'
}

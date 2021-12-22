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
    merge[0]=${libs}
    merge[1]=${main}
    # copy cpp files to a gather directory
    for ((idx=0; idx < ${#merge[@]}; idx++)); do
        ##act on ${merge[$idx]}
        cp -r ${merge[$idx]} ${workDir}'/build/.buildNatives'
    done
}

function compile() {
    cd ${workDir}'/shared'
    
    nativeSources=${workDir}'/build/.buildNatives/*'
    # append -lwiringPi for raspberry wiringPi includes
    g++ -fPIC ${nativeSources} -shared  -o ${clibName} \
                -I'/usr/lib/jvm/java-11-openjdk-amd64/include' \
                -I'/usr/lib/jvm/java-11-openjdk-amd64/include/linux' \
                -I${workDir}'/code/natives/includes' \
                
    rm $nativeSources
} 

function setJavaLibSource() {
    java.library.path=${workDir}'/shared'
}

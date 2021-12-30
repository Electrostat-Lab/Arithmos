#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/#* gtkmm-4.2.0/gtk"'
#* 
#*  Supports android natives @2022. 
#*
#* @author pavl_g.
#*#
source variables.sh
# Compile C++ code & bind to the header created from java
if [[ ! -d ${workDir}'/build/.buildNatives' ]]; then
    mkdir ${workDir}'/build/.buildNatives'
fi

function copyNativeSources() {
    # dir to compile & sharedLib name
    # copy cpp files to a gather directory
    cd ${workDir}'/code/natives/libs'
    libs=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    if [[ ${libs} ]]; then
        chmod +x $libs
        cp ${libs} ${workDir}'/build/.buildNatives'
    fi
    
    cd ${workDir}'/code/natives/main'
    main=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    if [[ ${main} ]]; then
        chmod +x $main
        cp ${main} ${workDir}'/build/.buildNatives'
    fi    
}

function compile() {
    cd ${workDir}'/build/.buildNatives'
    nativeSources=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    # tests if the sources exist, then give the current user full permissions on them and compile them
    if [[ ${nativeSources} ]]; then  
        chmod +x $nativeSources
        # append -lwiringPi for raspberry wiringPi includes
        # ${JAVA__HOME%/*} : % returns back to the root base directory of the java home, / is the separator delimiter of the directory string
        linux_x86_x64 "${nativeSources}"
        if [[ $enable_android_build == true ]]; then
             linux_android "${arm64}" "${arm64_lib}" "${nativeSources}" "${min_android_sdk}"
             linux_android "${arm32}" "${arm32_lib}" "${nativeSources}" "${min_android_sdk}"
             linux_android "${intel64}" "${intel64_lib}" "${nativeSources}" "${min_android_sdk}"
             linux_android "${intel32}" "${intel32_lib}" "${nativeSources}" "${min_android_sdk}"
        fi

        rm $nativeSources
    fi  
} 
##
# Build for desktop linux systems
##
function linux_x86_x64() {
    if [[ ! -d ${workDir}'/shared/linux-x86-x64' ]]; then
        mkdir ${workDir}'/shared/linux-x86-x64'
    fi
    g++ -fPIC $1 -shared -o ${workDir}'/shared/linux-x86-x64/'${clibName} \
            -I${JAVA__HOME%/*}'/include' \
            -I${JAVA__HOME%/*}'/include/linux' \
            -I${workDir}'/code/natives/includes' 
}
##
# Building native code for arm and intel android.
##
function linux_android() {
    # parameters attributes
    local triple=$1
    local folder=$2
    local sources=$3
    local min_android_sdk=$4
    
    if [[ ! -d ${workDir}"/shared/lib" ]]; then
        mkdir ${workDir}"/shared/lib"
    fi
    
    if [[ ! -d ${workDir}"/shared/lib/$folder" ]]; then
        mkdir ${workDir}"/shared/lib/$folder"
    fi
    $NDK__BASEHOME'/toolchains/llvm/prebuilt/linux-x86_64/bin/clang++' -target ${triple}${min_android_sdk} \
        -fPIC $sources -shared \
        -stdlib=libstdc++ \
        -o ${workDir}"/shared/lib/$folder/"${clibName} \
        -I${workDir}'/code/natives/includes' \
        -I$NDK__BASEHOME'/sources/cxx-stl/llvm-libc++/include' \
        -lc++_shared
    cp $NDK__BASEHOME"/sources/cxx-stl/llvm-libc++/libs/${folder}/libc++_shared.so" ${workDir}"/shared/lib/$folder"
}

function setJavaLibSource() {
    java.library.path=${workDir}'/shared'
}

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* 
#* @author pavl_g.
#*#
source variables.sh

if [[ ! -d ${workDir}'/build/.buildNatives' ]]; then
    mkdir ${workDir}'/build/.buildNatives'
fi

##
# Copies all native sources to the build directory using 'the find'.
# @echo Script Succeeded if one command has passed successfully, exit with error code if non of them has passed.
##
function copyNativeSources() {
    # dir to compile & sharedLib name
    # copy cpp files to a gather directory
    errors=$(( 0 ))
    cd ${workDir}'/code/natives/libs'
    libs=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    if [[ ${libs} ]]; then
        chmod +x $libs
        cp ${libs} ${workDir}'/build/.buildNatives'
    else 
        errors=$(( errors + 1 ))
    fi
    
    cd ${workDir}'/code/natives/main'
    main=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    if [[ ${main} ]]; then
        chmod +x $main
        cp ${main} ${workDir}'/build/.buildNatives'
    else 
        errors=$(( errors + 1 ))
    fi
    
    if (( $errors > 1 )); then  
        echo -e "$RED---MajorTask@Build Native Sources : No native sources to build---"
        exit 1200
    else
        echo -e "$GREEN---MajorTask@Build Native Sources : Found native sources---"
    fi
}

##
# Compile and build native sources.
# @echo Script Succeeded if all the commands have passed successfully, exit with error code otherwise.
##
function compile() {
    cd ${workDir}'/build/.buildNatives'
    nativeSources=`find -name '*.c' -o -name '*.cxx' -o -name '*.cpp' -o -name '*.h' -o -name '*.c++'`
    # tests if the sources exist, then give the current user full permissions on them and compile them
    if [[ ${nativeSources} ]]; then  
        chmod +x $nativeSources
        # append -lwiringPi for raspberry wiringPi includes
        # ${JAVA__HOME%/*} : % returns back to the root base directory of the java home, / is the separator delimiter of the directory string
        # compile and build a shared lib for linux systems
        if [[ `linux_x86_x64 "${nativeSources}"` -eq 0 ]]; then
            echo -e "$GREEN Task@Build Linux-x86-x64 : Succeeded"
        else
            echo -e "$RED Task@Build Linux-x86-x64 : Failed"
            echo -e "$RED Exiting Script with error 150"
            exit 150
        fi
        # compile and build a shared lib for android systems
        if [[ $enable_android_build == true ]]; then
             if [[ `linux_android "${arm64}" "${arm64_lib}" "${nativeSources}" "${min_android_sdk}"` -eq 0 ]]; then
                echo -e "$GREEN Task@Build Android-Arm-64 : Succeeded"
             else
                echo -e "$RED Task@Build Android-Arm-64 : Failed"
                echo -e "$RED Exiting Script with error 250"
                exit 250
             fi
             
             if [[ `linux_android "${arm32}" "${arm32_lib}" "${nativeSources}" "${min_android_sdk}"` -eq 1 ]]; then 
                echo -e "$GREEN Task@Build Android-Arm-32 : Succeeded"
             else
                echo -e "$RED Task@Build Android-Arm-32 : Failed"
                echo -e "$RED Exiting Script with error 350"
                exit 350
             fi
             
             if [[ `linux_android "${intel64}" "${intel64_lib}" "${nativeSources}" "${min_android_sdk}"` -eq 0 ]]; then
                echo -e "$GREEN Task@Build Android-Intel-64 : Succeeded"
             else 
                echo -e "$RED Task@Build Android-Intel-64 : Failed"
                echo -e "$RED Exiting Script with error 450"
                exit 450
             fi
             
             if [[ `linux_android "${intel32}" "${intel32_lib}" "${nativeSources}" "${min_android_sdk}"` -eq 0 ]]; then 
                echo -e "$GREEN Task@Build Android-Intel-32 : Succeeded"
             else
                echo -e "$RED Task@Build Android-Intel-32 : Failed"
                echo -e "$RED Exiting Script with error 550"
                exit 550
             fi
        fi

        rm $nativeSources
    fi
    echo -e "$GREEN ---MajorTask@Build Native Sources : Succeeded---"
} 

##
# Build for desktop linux systems
# @param nativeSources sources to be compiled for linux desktop.
# @return 0 if command passes, non zero number otherwise with exit code 150 (search the code on repo's wiki).
##
function linux_x86_x64() {
    local nativeSources=$1
    if [[ ! -d ${workDir}'/shared/linux-x86-x64' ]]; then
        mkdir ${workDir}'/shared/linux-x86-x64'
    fi
    g++ -fPIC $nativeSources -shared -o ${workDir}'/shared/linux-x86-x64/'${clibName} \
        -I${JAVA__HOME%/*}'/include' \
        -I${JAVA__HOME%/*}'/include/linux' \
        -I${workDir}'/code/natives/includes' 
    return $?
}
##
# Building native code for arm and intel android.
# @param triple the ABI triple name, also used for -target flag of the clang++.
# @param folder the created folder name.
# @param sources the sources to compile and build an object file for them.
# @param min_android_sdk the minimum android sdk to compile against.
# @return 0 if command passes, non zero number otherwise.
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
    ndkBuildingCommand=$?
    cp $NDK__BASEHOME"/sources/cxx-stl/llvm-libc++/libs/${folder}/libc++_shared.so" ${workDir}"/shared/lib/$folder"
    return $ndkBuildingCommand
}

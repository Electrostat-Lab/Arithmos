#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

source variables.sh

buildDir=${workingDir}'/build/.buildJava/'
dependencies=$java_resources'/dependencies'
assets=$java_resources'/assets'

#**
#* Makes an output directory at the output location.
#* @return the number of errors.
#**
function makeOutputDir() {
    local errors
    cd ${buildDir}
    if [[ ! -d ${outputJAR} ]]; then
        mkdirectory=`mkdir ${outputJAR}`
        if (( mkdirectory != 0 )); then
            errors=$(( $errors + 1 ))
        fi
    fi
    return $errors
}

#**
#* Creates a Manifest.mf file for the jar main class designation and dependencies inclusion.
#* @return the number of errors, 0 if no errors, 1 or 2 if there are errors.
#**
function createManifest() {
    local errors=0
    cd ${buildDir}''${outputJAR}
    if [[ ! `echo ${manifest} > Manifest.mf` -eq 0 ]]; then
        errors=$(( $errors + 1 ))
    fi
    if [[ ! `echo ${mainclass} >> Manifest.mf` -eq 0 ]]; then
        errors=$(( $errors + 1 ))
    fi
    return $errors
}

#**
#* Adds the dependencies to the dependencies directory at the code/java/dependencies relative path.
#* @return the number of errors, 0 if no errors, 1 or more if there are errors.
#**
function addDependencies() {
    local errors=0
	cd $dependencies
	jars=`find -name '*.jar'`
    if [[ ! `cp ${jars} ${buildDir}''${outputJAR}` -eq 0 ]]; then 
        errors=$(( $errors + 1 ))
    fi
    cd ${buildDir}''${outputJAR}'/'
    printf '%s ' ${classpath} >> ${buildDir}''${outputJAR}'/Manifest.mf'
    printf ' %s \n' ${jars[0]} >> ${buildDir}''${outputJAR}'/Manifest.mf'
    errors=$(( $errors + $? ))

    return $errors
}

#**
#* Adds the android native dependencies (the .so native object files) as a jar dependency 
#* at the output/Arithmos/dependencies relative path. 
#* @return the number of errors, 0 if no errors, 1 or 2 if there are errors.
#**
function addAndroidNativeDependencies() {
    local errors=0
    # get the object files to link them
    cd ${workingDir}'/shared'
    if [[ ! `zip -r "android-natives-${min_android_sdk}.jar" . -i "lib/*"` -eq 0 ]]; then
        errors=$(( $errors + 1 ))
    fi
    
    nativeLibs=${workingDir}"/shared/android-natives-${min_android_sdk}.jar"
    if [[ $nativeLibs ]]; then
        # copy the object file to the build dir
        if [[ ! `mv $nativeLibs $buildDir''${outputJAR}'/'` -eq 0 ]]; then
            errors=$(( $errors + 1 ))
        else
		    printf ' %s \n' "./android-natives-${min_android_sdk}.jar" >> ${buildDir}''${outputJAR}'/Manifest.mf'
		fi    
    fi
    return $errors
}

#**
#* Adds the linux native dependencies (the .so native object files)
#* at the output/Arithmos relative path. 
#* @return the number of errors, 0 if no errors, 1 or 2 if there are errors.
#**
function addLinuxNativeDependencies() {
    local errors=0
	shared=${workingDir}"/shared"
    cd $shared
	
    if [[ $shared ]]; then
        # copy the object file to the build dir
        if [[ ! `cp -r $shared'/' $buildDir''${outputJAR}'/'` -eq 0 ]]; then
            errors=$(( $errors + 1 ))
        else
			libs=`find -name '*.so' -o -name '*.dylb' -o -name '*.a' -o -name '*.dll' -o -name '*.DLL'`
		    printf ' %s \n' $libs >> ${buildDir}''${outputJAR}'/Manifest.mf'
		fi    
    fi   
    return $errors
}

function addAssets() {
    local errors=1 
    if [[ -f $assets ]]; then
        if [[ ! `mkdir ${outputJAR}'/assets'` -eq 0 ]]; then
            errors=$(( $errors + 1 ))
        fi
        assetsFolder=${buildDir}''${outputJAR}'/assets'
        # copy to an asset folder
        cp -r $assets $assetsFolder
        cd ${buildDir}''${outputJAR}
        # zip the assets into a jar file
        if [[ ! `zip -r assets.jar . -i 'assets/*'` -eq 0 ]]; then
            errors=$(( $errors + 1 ))
        fi
        # remove the assets folder
        rm -rf 'assets'
    else 
        errors=$(( $errors + 1 ))
    fi
    return $errors
}

function createJar() {
    local errors=0
    # get the manifest file to link it
    cd $buildDir
    manifestFile=${outputJAR}'/Manifest.mf'
    # get the class files ready
    javaClasses=`find -name "*.class"`
    # command and output a jar file with linked manifest, java class files and object files
    if [[ ! `$command cmf ${manifestFile} ${outputJAR}'.jar' ${javaClasses}` -eq 0 ]]; then 
        errors=$(( $errors + 1 ))
    fi
	${JAVA__HOME}'/jar' uf ${outputJAR}'.jar' -C $buildDir''${outputJAR}'/shared/' .
	rm -r $buildDir''${outputJAR}'/shared/'
    # move the jar to its respective output folder
    mv ${outputJAR}'.jar' $buildDir''${outputJAR}
    # move the jar directory containing the jar and the assets to the output directory
    mv $buildDir''${outputJAR} $workingDir'/output'
    # remove the residual manifest file
    cd $workingDir'/output/'${outputJAR}
    rm 'Manifest.mf'
    return $errors
}

#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

source variables.sh

outputJARDir=${workingDir}'/build/.buildJava/'

function makeOutputDir() {
    cd ${outputJARDir}
    
    if [[ ! -f ${outputJAR} ]]; then
        mkdir ${outputJAR}
    fi
    
    if [[ ! -d ${workingDir}'/code/java/dependencies' ]]; then
        mkdir ${workingDir}'/code/java/dependencies'
    fi
    
}

function createManifest() {
    cd ${outputJARDir}''${outputJAR}
    echo ${manifest} > Manifest.mf
    echo ${mainclass} >> Manifest.mf
}

function addDependencies() {
    dependencies=${workingDir}'/code/java/dependencies'
    if [[ $dependencies ]]; then
        cp -r ${dependencies} ${outputJARDir}''${outputJAR}'/dependencies'
        cd ${outputJARDir}''${outputJAR}'/'
        jars=('dependencies/*.jar') 
        printf '%s ' ${classpath} >> ${outputJARDir}''${outputJAR}'/Manifest.mf'
        printf ' %s \n' ${jars[0]} >> ${outputJARDir}''${outputJAR}'/Manifest.mf'
    fi
}

function addNativeDependencies() {
    # get the object files to link them
    cd ${workingDir}'/shared'
    zip -r "android-natives-${min_android_sdk}.jar" . -i 'lib/*' 
    
    nativeLibs=${workingDir}"/shared/android-natives-${min_android_sdk}.jar"
    if [[ $nativeLibs ]]; then
        # copy the object file to the build dir
        mv $nativeLibs $outputJARDir''${outputJAR}'/dependencies/'    
    fi
}

function addAssets() {
    assets=${workingDir}'/code/java/assets/*'
    if [[ -f $assets ]]; then
        mkdir ${outputJAR}'/dependencies/assets'
        assetsFolder=${outputJARDir}''${outputJAR}'/dependencies/assets'
        # copy to an asset folder
        cp -r $assets $assetsFolder
        cd ${outputJARDir}''${outputJAR}'/dependencies'
        # zip the assets into a jar file
        zip -r assets.jar . -i 'assets/*'
        # remove the assets folder
        rm -rf 'assets'
    fi
}

function createJar() {
    # get the manifest file to link it
    cd $outputJARDir
    manifestFile=${outputJAR}'/Manifest.mf'
    # get the class files ready
    javaClasses=`find -name "*.class"`
    # command and output a jar file with linked manifest, java class files and object files
    $command cmf ${manifestFile} ${outputJAR}'.jar' ${javaClasses}
    # move the jar to its respective output folder
    mv ${outputJAR}'.jar' $outputJARDir''${outputJAR}
    # move the jar directory containing the jar and the assets to the output directory
    mv $outputJARDir''${outputJAR} $workingDir'/output'
    # remove the residual manifest file
    cd $workingDir'/output/'${outputJAR}
    rm 'Manifest.mf'
}

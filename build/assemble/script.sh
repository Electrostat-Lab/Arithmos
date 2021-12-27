#**
#* Ccoffee Build tool, manual build, alpha-v1.
#*
#* @author pavl_g.
#*#

source variables.sh

outputJARDir=${workingDir}'/build/.buildJava/'

function makeOutputDir() {
    cd ${outputJARDir}
    mkdir ${outputJAR}
    mkdir ${outputJAR}'/dependencies'
    mkdir ${outputJAR}'/dependencies/assets'
}

function createManifest() {
    cd ${outputJARDir}''${outputJAR}
    cat Manifest.mf
    echo ${manifest} > Manifest.mf
    echo ${mainclass} >> Manifest.mf
}

function addDependencies() {
    dependencies=${workingDir}'/code/java/dependencies/*'
    cp -r ${dependencies} ${outputJARDir}''${outputJAR}'/dependencies'
    cd ${outputJARDir}''${outputJAR}'/'
    jars=('dependencies/*.jar') 
    printf '%s ' ${classpath} >> ${outputJARDir}''${outputJAR}'/Manifest.mf'
    printf ' %s \n' ${jars[0]} >> ${outputJARDir}''${outputJAR}'/Manifest.mf'
}

function addAssets() {
    assets=${workingDir}'/code/java/assets/*'
    assetsFolder=${outputJARDir}''${outputJAR}'/dependencies/assets'
    # copy to an asset folder
    cp -r $assets $assetsFolder
    cd ${outputJARDir}''${outputJAR}'/dependencies'
    # zip the assets into a jar file
    zip -r assets.jar . -i 'assets/*'
    # remove the assets folder
    rm -rf 'assets'
}

function createJar() {
    # get the object files to link them
    cd ${workingDir}'/shared'
    nativeLibs=`find -name "*.so"`
    # copy the object file to the build dir
    cp $nativeLibs $outputJARDir''${outputJAR}
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

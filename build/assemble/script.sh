#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
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
    cd $outputJARDir
    manifestFile=${outputJAR}'/Manifest.mf'
    javaClasses='*/*.class'
    nativeLibs=${workingDir}'/shared/*.so'
    # copy the object file to the build dir
    cp $nativeLibs $outputJARDir''${outputJAR}
    
    $command cmf ${manifestFile} ${outputJAR}'.jar' ${javaClasses} ${nativeLibs}
    
    mv ${outputJAR}'.jar' $outputJARDir''${outputJAR}
    
    mv $outputJARDir''${outputJAR} $workingDir'/output'

}

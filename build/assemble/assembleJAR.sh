#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*# 

# Sanity check the build directory
source variables.sh

buildDir=${workingDir}'/build/.buildJava'

if [[ ! -d $buildDir ]]; then 
    chmod +x ${workingDir}'/build/compile/compile.sh'
    cd ${workingDir}'/build/compile'
    ./'compile.sh'
fi

echo "Assemble JAR"
echo -e $RESET_Cs
echo "--------Script start--------"
cd ${workingDir}'/build/assemble'
source script.sh
source clean.sh

clean=`clean`
if (( clean > 0 )); then 
    echo -e "$RED_C Task@Clean : Failed"
else 
    echo -e "$CYAN_C Task@Clean : Completed"
fi

echo -e $RESET_Cs

makeOutputDir=`makeOutputDir`
if ((  makeOutputDir > 0 ));  then
    echo -e "$RED_C Task@MakeOutputDirectory : Failed"
else
    echo -e "$WHITE_C Task@MakeOutputDirectory : Completed"
fi

echo -e $RESET_Cs

createManifest=`createManifest`
if (( createManifest > 0 )); then
    echo -e "$RED_C Task@CreateJarManifest : Failed"
else
    echo -e "$ORANGE_C Task@CreateJarManifest : Completed"
fi

echo -e $RESET_Cs

addDependencies=`addDependencies`
if (( addDependencies > 0 )); then
    echo -e "$RED_C Task@AddJavaDependencies : Failed"
else
    echo -e "$ORANGE_C Task@AddJavaDependencies : Completed"
fi

echo -e $RESET_Cs

addLinuxNativeDependencies=`addLinuxNativeDependencies`
if (( addLinuxNativeDependencies > 0 )); then 
    echo -e "$RED_C Task@AddLinuxNativeDependencies : Failed"
else
    echo -e "$MAGNETA_C Task@AddLinuxNativeDependencies : Completed"
fi

echo -e $RESET_Cs

addAndroidNativeDependencies=`addAndroidNativeDependencies`
if (( addAndroidNativeDependencies > 0 )); then 
    echo -e "$RED_C Task@AddNativeDependencies : Failed"
else
    echo -e "$MAGNETA_C Task@AddNativeDependencies : Completed"
fi

echo -e $RESET_Cs

addAssets=`addAssets`
if (( addAssets > 0 )); then 
    echo -e "$RED_C Task@AddAssets : Failed -- AssetsNotFound"
else
    echo -e "$ORANGE_C Task@AddAssets : Completed"
fi

echo -e $RESET_Cs

createJar=`createJar`
if (( createJar > 0 )); then 
    echo -e "$RED_C Task@CreateJar : Failed"
else
    echo -e "$ORANGE_C Task@CreateJar : Completed"
fi

echo -e $RESET_Cs

echo "--------Script end--------"

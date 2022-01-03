#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*#

echo "Compiling the project"

source buildScala.sh
source buildKotlin.sh
source buildGroovy.sh
source buildJava.sh
source buildNatives.sh

if [[ $enable_scala_build == true ]]; then
    echo -e "$WHITE_C---MajorTask@Build Scala : Scala build started"
    copyScSources
    if [[ `compileScala` -eq 0 ]]; then
        echo -e "$GREEN_C Task@Build Compile Scala : Succeeded"
    else 
        echo -e "$RED_C Task@Build Compile Scala : Failed"
    fi
    echo -e "$WHITE_C---MajorTask@Build Scala : Scala build finished"
fi

if [[ $enable_kt_build == true ]]; then
    copyKtSources

    compileKotlin
fi

if [[ $enable_groovy_build == true ]]; then
    copyGroovySources

    compileGroovy
fi

if [[ $enable_java_build == true ]]; then
    copyJavaSources

    generateHeaders

    moveHeaders
fi

if [[ $enable_natives_build == true ]]; then
    echo -e "$WHITE_C---MajorTask@Build Native Sources : Native build started"
    copyNativeSources
    compile
    echo -e "$WHITE_C---MajorTask@Build Native Sources : Native build finished"
fi

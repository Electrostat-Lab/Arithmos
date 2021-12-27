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
    copyScSources

    compileScala
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
    copyNativeSources

    compile
fi

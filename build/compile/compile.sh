#**
#* Ccoffee Build tool, manual build, alpha-v1.
#* Custom Includsions for GTKmm cpp wrapper
#* dependencies '-I"/usr/include/glibmm-2.9.1/glib" -I"/usr/include/sigc++-2.0/sigc++" -I"/usr/include/giomm-2.4" -I"/usr/include/gtkmm-4.2.0/gtk"'
#*
#* @author pavl_g.
#*#

echo "Compiling the project"
echo -e $RESET_Cs
echo "--------Script start--------"
source buildScala.sh
source buildKotlin.sh
source buildGroovy.sh
source buildJava.sh
source buildNatives.sh

echo -e $RESET_Cs

if [[ $enable_scala_build == true ]]; then
    echo -e "$BRIGHT_RED_C---MajorTask@Build Scala : Scala build started"
    copyScSources
    if [[ `compileScala` -eq 0 ]]; then
        echo -e "$GREEN_C Task@Build Compile Scala : Succeeded"
    else 
        echo -e "$RED_C Task@Build Compile Scala : Failed"
    fi
    echo -e "$BRIGHT_RED_C---MajorTask@Build Scala : Scala build finished"
fi

echo -e $RESET_Cs

if [[ $enable_kt_build == true ]]; then
    echo -e "$VIOLET_C---MajorTask@Build Kotlin : Kotlin build started"
    copyKtSources
    if [[ `compileKotlin` -eq 0 ]]; then
        echo -e "$GREEN_C Task@Build Compile Kotlin : Succeeded"
    else
        echo -e "$RED_C Task@Build Compile Kotlin : Failed"
    fi
    echo -e "$VIOLET_C---MajorTask@Build Kotlin : Kotlin build finished"
fi

echo -e $RESET_Cs

if [[ $enable_groovy_build == true ]]; then
    echo -e "$CYAN_C---MajorTask@Build Groovy : Groovy build started"
    copyGroovySources
    if [[ `compileGroovy` -eq 0 ]]; then
        echo -e "$GREEN_C Task@Build Compile Groovy : Succeeded"
    else
        echo -e "$RED_C Task@Build Compile Groovy : Failed"
    fi
    echo -e "$CYAN_C---MajorTask@Build Groovy : Groovy build finished"
fi

echo -e $RESET_Cs

if [[ $enable_java_build == true ]]; then
    echo -e "$ORANGE_C---MajorTask@Build Java Sources : Java build started"
    copyJavaSources
    if [[ `generateHeaders` -eq 0 ]]; then
       moveHeaders
       echo -e "$GREEN_C Task@Build Compile Java : Succeeded"
    else
        echo -e "$RED_C Task@Build Compile Java : Failed"
    fi
    echo -e "$ORANGE_C---MajorTask@Build Java Sources : Java build finished"
fi

echo -e $RESET_Cs

if [[ $enable_natives_build == true ]]; then
    echo -e "$MAGNETA_C---MajorTask@Build Native Sources : Native build started"
    copyNativeSources
    compile
    echo -e "$MAGNETA_C---MajorTask@Build Native Sources : Native build finished"
fi

echo -e $RESET_Cs
echo "--------Script end--------"

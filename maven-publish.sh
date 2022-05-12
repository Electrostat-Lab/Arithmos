project_root=`pwd`
source $project_root"/build/compile/variables.sh"

function deploy() {
	local artifactId=$1
	local jar=$2
	
	mvn deploy:deploy-file -DgroupId=com.arithmos-algorithms.arithmos \
						   -DartifactId=$artifactId \
						   -Dversion=1.0.3-ALPHA \
						   -Dpackaging=jar \
						   -Dfile='./output/Arithmos/'$jar \
						   -DrepositoryId=github \
						   -Durl=https://maven.pkg.github.com/Arithmos-algorithms/Arithmos

}

deploy "arithmos" "Arithmos.jar"
deploy "arithmos-natives-android" "android-natives-${min_android_sdk}.jar"
deploy "arithmos-groovy" "groovy.jar"
deploy "arithmos-kotlin" "kotlin.jar"
deploy "arithmos-scala" "scala.jar"
deploy "arithmos-assets" "assets.jar"
deploy "groovy-2.4.7" "groovy-2.4.7.jar"
deploy "scala-2.13.7" "scala-library-2.13.7.jar"

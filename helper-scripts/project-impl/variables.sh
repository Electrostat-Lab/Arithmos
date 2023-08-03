#!/bin/sh

# Maven sonatype stuff
# ---------------------
sonatype_url="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
repository="ossrh"
groupId="io.github.software-hardware-codesign"
maven_version="3.9.3"
maven_bin="./apache-maven-$maven_version/bin/mvn"

lib_pomFile="./helper-scripts/project-impl/publishing/arithmos.pom"

passphrase="avrsandbox"

lib_artifactId_release="arithmos"

artifactId_debug="arithmos-debug"

settings="./helper-scripts/project-impl/publishing/maven-settings.xml"
lib_build_dir="./arithmos/build/libs"
ext_build_dir="./arithmos-monkey/build/libs"
# ---------------------

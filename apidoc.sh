#!/usr/bin/env bash

workspace=$(pwd)
appName=$(basename ${workspace})
env=$1

logDir=${workspace}/log
targetDir=${workspace}/target

echo "<<<<<<<<<<<<<<<<<<<<<<<<"
echo "build apidoc ..."
echo ">>>>>>>>>>>>>>>>>>>>>>>>"

apiTemplateDir=${workspace}/apidoc/${env}
apiName=${appName/%-demo/-doc}


apidoc -i ${workspace}/src/main//java/com/spring/boot/luggage_claims_system/hirbernia_sina/controller -o ${targetDir}/${apiName} -c ${apiTemplateDir}
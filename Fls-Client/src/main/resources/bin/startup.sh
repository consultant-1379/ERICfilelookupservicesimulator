#!/bin/bash
################################################################################
# COPYRIGHT Ericsson 2018
#
# The copyright to the computer program(s) herein is the property of
# Ericsson Inc. The programs may be used and/or copied only with written
# permission from Ericsson Inc. or in accordance with the terms and
# conditions stipulated in the agreement/contract under which the
# program(s) have been supplied.
################################################################################

###################################################
# Version no    :  OSS
# Purpose       :  The purpose of this script to run FLS Client Jar
# Jira No       :
# Gerrit Link   :
# Description   :  Created the script as a part of the FLS 
# Date          :  05/01/2018
# Last Modified :  dheeraj.m@tcs.com
####################################################
FLS_CLIENT_PROPERTIES="../config/flsClient.properties"
LOG4J_PROPERTIES="../config/log4j.properties"
FLS_LIB="../lib/*"
FLS_CLIENT_JAR="../lib/Fls-Client-1.0.jar"
MAIN_CLASS="com.ericsson.fls.client.main.FlsClient"

java -cp ${FLS_LIB}:${FLS_CLIENT_JAR} -DpropertyFile=${FLS_CLIENT_PROPERTIES} -Dlog4j.configuration=${LOG4J_PROPERTIES} ${MAIN_CLASS}
